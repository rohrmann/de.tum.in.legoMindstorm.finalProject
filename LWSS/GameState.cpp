/*
 * GameMap.cpp
 *
 *  Created on: Sep 16, 2010
 *      Author: rohrmann
 */

#include "GameState.h"
#include <utility>
#include "ZobristHashing.h"
#include <stack>
#include <queue>
#include "MapUtils.h"
#include <string.h>
#include "ComponentTester.h"
#include "MapAlgorithms.h"


unsigned int GameState::numTargets =0;
std::pair<dimension,dimension>* GameState::targets = NULL;
std::unordered_set< std::pair<dimension,dimension>, PointHasher, PointEqual > GameState::targetsHash;
int GameState::instanceCounter=0;

GameState::~GameState(){
	delete [] boxes;

	if(prev!=NULL){
		prev->children--;
	}

	if(robotMovement != NULL){
		delete robotMovement;
	}

	if(boxMovement != NULL){
		delete boxMovement;
	}
}

GameState::GameState(){
	init(make_point(0,0),make_point(0,0),make_point(0,0),make_point(0,0),NULL,0,0,1,NULL);
}

GameState::GameState(field* map,std::pair<dimension,dimension> dims) : Map<field>(map,dims){
	init(make_point(0,0),make_point(0,0),make_point(0,0),make_point(0,0),NULL,0,0,1,NULL);
}

GameState::GameState(point puller,point pullerTL,point pusher,point pusherTL,point* boxes, unsigned int numBoxes,int estimatedCosts,field componentValue,GameState* prev,field* map,point dims) : Map<field>(map,dims){
	init(puller,pullerTL,pusher,pusherTL,boxes,numBoxes,estimatedCosts,componentValue,prev);
}

void GameState::init(point puller,point pullerTL,point pusher,point pusherTL,point* boxes, unsigned int numBoxes,int estimatedCosts,field componentValue,GameState* prev){
	this->puller = puller;
	this->pullerTL = pullerTL;
	this->pusher = pusher;
	this->pusherTL = pusherTL;
	this->boxes = boxes;
	this->numBoxes = numBoxes;
	this->estimatedCosts = estimatedCosts;
	this->componentValue = componentValue;

	this->boxMovement = NULL;
	this->robotMovement = NULL;

	this->prev = prev;
	this->children = 0;

	if(prev != NULL){
		prev->children++;
		this->level = prev->level +1;
	}
	else{
		this->level = 0;
	}

	instanceCounter++;
}

bool GameState::solved(){
	for(unsigned int i= 0; i< numBoxes;i++){
		if(GameState::targetsHash.find(boxes[i]) == GameState::targetsHash.end())
			return false;
	}
	return true;
}

void GameState::setPrev(GameState* prev){
	if(this->prev !=NULL){
		this->prev->children--;
	}

	this->prev = prev;
	this->level = this->prev->level + 1;
	this->prev->children++;
}

GameState* GameState::copy(){
	point* newBoxes = new point[numBoxes];
	field* newMap = new field[dims.first*dims.second];

	memcpy(newBoxes,boxes,sizeof(point)*numBoxes);
	memcpy(newMap,map,sizeof(field)*dims.first*dims.second);

	return new GameState(puller,pullerTL,pusher,pusherTL,newBoxes,numBoxes,estimatedCosts,componentValue,prev,newMap,dims);
}

void GameState::apply(RobotMovement* movement){
	movement->doMove(this);

	if(robotMovement ==NULL){
		robotMovement = movement;
	}
	else
		robotMovement->insert(movement);
}

void GameState::apply(BoxMovement* movement){
	movement->doMove(this);

	boxMovement = movement;
}

void GameState::updateTL(){
	bool pullerFound = false;
	bool pusherFound = false;
	field pullerField = this->at(puller);
	field pusherField = this->at(pusher);

	for(dimension i = 0 ; i< dims.first;i++){
		for(dimension j =0; j<dims.second;j++){
			if(!pullerFound && this->at(i,j) == pullerField){
				pullerTL = make_point(i,j);
				pullerFound = true;
			}

			if(!pusherFound && this->at(i,j) == pusherField){
				pusherTL = make_point(i,j);
				pusherFound = true;
			}

			if(pusherFound && pullerFound)
				break;
		}
	}
}

void GameState::moveBot(Robot type, point pos){
	switch(type){
	case RPUSHER:
		return movePusher(pos);
	case RPULLER:
		return movePuller(pos);
	}
}

void GameState::movePuller(point pos){
	puller = pos;
}

void GameState::movePusher(point pos){
	pusher = pos;
}

void GameState::moveBox(unsigned int boxNum, point pos){
	this->set(boxes[boxNum],1);
	this->boxes[boxNum] = pos;
	this->set(pos,0);
	updateComponents();
}

HashValue<HASHLENGTH> GameState::getHashValue(ZobristHashing<HASHLENGTH>* hashing){
	HashValue<HASHLENGTH> result;

	for(unsigned int i = 0; i< numBoxes;i++){
		result ^= hashing->getBox(boxes[i]);
	}

	updateTL();

	result ^= hashing->getPuller(pullerTL);
	result ^= hashing->getPusher(pusherTL);

	return result;
}

void GameState::updateComponents(){

	for(unsigned int x =0 ; x < dims.first*dims.second;x++){
		if(map[x] != 0){
			map[x] = 255;
		}
	}

	field component =1;
	ComponentTester tester(this);

	for(dimension x = 0; x< dims.first;x++){
		for(dimension y = 0; y < dims.second;y++){
			if(at(x,y) == 255){
				dfs(make_point(x,y),map,component++,tester);
			}
		}
	}
}

void GameState::calcEstimatedCosts(){
	estimatedCosts= 0;
	bool* used = new bool[GameState::numTargets];
	for(unsigned int i =0; i< GameState::numTargets;i++){
		used[i] = false;
	}

	for(unsigned int i = 0; i< this->numBoxes;i++){
		unsigned int index = -1;
		int dist = 255;
		for(unsigned int j = 0; j< this->numTargets;j++){
			if(!used[j] && calcManhattanDist(this->boxes[i], this->targets[j]) < dist){
				dist = calcManhattanDist(this->boxes[i],this->targets[j]);
				index = j;
			}
		}

		used[index] = true;

		estimatedCosts += dist;
	}

	delete [] used;
}

unsigned char GameState::getFingerPrint(point pos){
	unsigned char result = 0;
	unsigned int x = pos.first-1;
	unsigned int y = pos.second-1;

	for(unsigned int i =0; i< 3;i++){
		if(this->at(x,y+i)==0){
			result |= 1<<i;
		}
	}

	if(this->at(x+1,y)==0){
		result |= 1<<3;
	}

	if(this->at(x+1,y+2)==0){
		result |= 1 << 4;
	}

	for(unsigned int i =0 ; i< 3;i++){
		if(this->at(x+2,y+i)==0){
			result |= 1 << (5 + i);
		}
	}

	return result;
}


field GameState::getNextComponentValue(){
	return componentValue++;
}

bool GameState::bfs(point start, point dest, std::unordered_set<point,PointHasher,PointEqual>& nodes){
	std::queue<point> queue;
	unsigned int * prev = new unsigned int[dims.first*dims.second];
	bool* visited = new bool[dims.first*dims.second];

	for(unsigned int i=0;i<dims.first*dims.second;i++){
		prev[i] = i;
		visited[i] = false;
	}



	queue.push(start);
	visited[addr(start)] = true;
	point pos = start;

	while(!queue.empty()){
		pos = queue.front();
		queue.pop();

		if(pos == dest)
			break;

		if(at(Helper::north(pos)) != 0 && !visited[addr(Helper::north(pos))]){
			visited[addr(Helper::north(pos))] = true;
			point newPos = Helper::north(pos);
			queue.push(newPos);
			prev[addr(newPos)] = addr(pos);
		}

		if(at(Helper::west(pos))!= 0 && !visited[addr(Helper::west(pos))]){
			visited[addr(Helper::west(pos))] = true;
			point newPos = Helper::west(pos);
			queue.push(newPos);
			prev[addr(newPos)] = addr(pos);
		}

		if(at(Helper::south(pos)) != 0 && !visited[addr(Helper::south(pos))]){
			visited[addr(Helper::south(pos))] = true;
			point newPos = Helper::south(pos);
			queue.push(newPos);
			prev[addr(newPos)] = addr(pos);
		}

		if(at(Helper::east(pos))!=0&& !visited[addr(Helper::east(pos))]){
			visited[addr(Helper::east(pos))]=true;
			point newPos = Helper::east(pos);
			queue.push(newPos);
			prev[addr(newPos)] = addr(pos);
		}
	}



	if(pos == dest){
		unsigned int destAddr = addr(dest);

		while(destAddr != prev[destAddr]){
			nodes.insert(make_point(destAddr/dims.second,destAddr%dims.second));
			destAddr = prev[destAddr];
		}

		nodes.insert(make_point(destAddr/dims.second,destAddr%dims.second));

	}

	delete [] prev;
	delete [] visited;

	return pos==dest;
}

bool GameState::findEvasionField(point start,const std::unordered_set<point, PointHasher, PointEqual>& nodes, point& result){
	bool* visited = new bool[dims.first*dims.second];
	std::queue<point> queue;
	bool found = false;

	for(unsigned int i =0; i < dims.first*dims.second;i++){
		visited[i] = false;
	}

	visited[addr(start)] = true;
	queue.push(start);

	point pos = start;

	while(!queue.empty()){

		pos = queue.front();
		queue.pop();

		if(nodes.find(pos) == nodes.end()){
			found = true;
			break;
		}

		if(at(Helper::north(pos)) != 0 && !visited[addr(Helper::north(pos))]){
			visited[addr(Helper::north(pos))] = true;
			queue.push(Helper::north(pos));
		}

		if(at(Helper::west(pos))!= 0 && !visited[addr(Helper::west(pos))]){
			visited[addr(Helper::west(pos))] = true;
			queue.push(Helper::west(pos));
		}

		if(at(Helper::south(pos))!= 0 && !visited[addr(Helper::south(pos))]){
			visited[addr(Helper::south(pos))] = true;
			queue.push(Helper::south(pos));
		}

		if(at(Helper::east(pos))!= 0 && !visited[addr(Helper::east(pos))]){
			visited[addr(Helper::east(pos))] = true;
			queue.push(Helper::east(pos));
		}
	}

	delete [] visited;

	if(found){
		result = pos;
		return true;
	}
	else
		return false;
}

RobotMovement* GameState::path(Robot type,point dest,std::unordered_set<point,PointHasher,PointEqual>& excludedNodes,unsigned int maxElements){
	return recPathfinding(type,(type==RPULLER? puller: pusher),(type==RPULLER? pusher:puller),dest,excludedNodes,maxElements);
}

RobotMovement* GameState::recPathfinding(Robot type, point botA, point botB, point dest,std::unordered_set<point, PointHasher, PointEqual>& excludedNodes, unsigned int maxElements){

	RobotMovement* movements =NULL;
	std::unordered_set<point,PointHasher,PointEqual> nodes;
	point oldBotA = make_point(0,0);

	unsigned int curElements = 1;

	while(maxElements > curElements){

		if(botA != oldBotA){
			nodes = excludedNodes;
			if(!bfs(botA,dest,nodes)){
				if(movements != NULL)
					delete movements;

				return NULL;
			}
			oldBotA = botA;
		}

		if(nodes.find(botB) == nodes.end()){
			if(movements==NULL)
				return new RobotMovement(type,dest,NULL);
			else{
				movements->insert(new RobotMovement(type,dest,NULL));
				return movements;
			}
		}
		else{
			point newDest;

			field savedComponent = at(botA);
			set(botA,0);
			if(!findEvasionField(botB,nodes,newDest)){
				set(botA,savedComponent);
				if(!findEvasionField(botB,nodes,newDest)){
					if(movements!= NULL)
						delete movements;
					return NULL;
				}
			}

			set(botA,savedComponent);

			std::unordered_set<point, PointHasher, PointEqual> dummy;
			RobotMovement* newMovements = recPathfinding((type==RPULLER?RPUSHER:RPULLER),botB,botA,newDest,dummy,maxElements-curElements);

			if(newMovements == NULL){
				if(movements!= NULL)
					delete movements;

				return NULL;
			}

			if(movements == NULL){
				movements = newMovements;
			}
			else
				movements->insert(newMovements);

			curElements = 1 + movements->count();

			botA = type == RPULLER? movements->getPuller(botA): movements->getPusher(botA);
			botB = type == RPULLER? movements->getPusher(botB): movements->getPuller(botB);
		}
	}

	if(movements!= NULL){
		delete movements;
	}

	return NULL;
}

void GameState::findAdjacentComponents(point point, unsigned int& numComponents, field* components){
	field component = at(Helper::north(point));
	bool found = false;

	if(component != 0){
		for(unsigned int i =0; i< numComponents;i++){
			if(components[i] == component)
				found = true;
		}

		if(!found){
			components[numComponents++] = component;
		}
	}

	component = at(Helper::west(point));
	found = false;

	if(component != 0){
		for(unsigned int i =0; i< numComponents;i++){
			if(components[i] == component)
				found = true;
		}

		if(!found){
			components[numComponents++] = component;
		}
	}

	component = at(Helper::east(point));
	found = false;

	if(component != 0){
		for(unsigned int i =0; i< numComponents;i++){
			if(components[i] == component)
				found = true;
		}

		if(!found){
			components[numComponents++] = component;
		}
	}

	component = at(Helper::south(point));
	found = false;

	if(component != 0){
		for(unsigned int i =0; i< numComponents;i++){
			if(components[i] == component)
				found = true;
		}

		if(!found){
			components[numComponents++] = component;
		}
	}
}

void GameState::findEvasionFields(point start, unsigned int numComponents,field* components,const std::unordered_set<point,PointHasher,PointEqual>&nodes,GameState* tempState,unsigned int & numEvasionFields,point* evasionFields){
	std::queue<point> queue;
	bool* visited = new bool[dims.first*dims.second];
	bool found[4];

	for(unsigned int i =0; i< 4;i++){
		found[i] = false;
	}

	for(unsigned int i =0; i< dims.first*dims.second;i++){
		visited[i] = false;
	}

	visited[addr(start)] = true;

	queue.push(start);

	while(!queue.empty()){
		point pos = queue.front();
		queue.pop();

		if(nodes.find(pos) == nodes.end()){
			field component = tempState->at(pos);

			for(unsigned int i = 0; i< numComponents;i++){
				if(found[i] == false && component == components[i]){
					found[i] = true;
					evasionFields[numEvasionFields++] = pos;
					break;
				}
			}
		}

		bool terminated = true;

		for(unsigned int i =0; i< numComponents;i++){
			if(!found[i]){
				terminated = false;
				break;
			}
		}

		if(terminated)
			break;

		if(at(Helper::north(pos)) != 0 && !visited[addr(Helper::north(pos))]){
			visited[addr(Helper::north(pos))] = true;
			queue.push(Helper::north(pos));
		}

		if(at(Helper::west(pos)) != 0 && !visited[addr(Helper::west(pos))]){
			visited[addr(Helper::west(pos))] = true;
			queue.push(Helper::west(pos));
		}

		if(at(Helper::south(pos)) != 0 && !visited[addr(Helper::south(pos))]){
			visited[addr(Helper::south(pos))] = true;
			queue.push(Helper::south(pos));
		}

		if(at(Helper::east(pos))!= 0 && !visited[addr(Helper::east(pos))]){
			visited[addr(Helper::east(pos))] = true;
			queue.push(Helper::east(pos));
		}
	}

	delete [] visited;
}

void GameState::printMovements(std::ostream & os){
	if(robotMovement != NULL){
		os << robotMovement->toString() << std::endl;
	}

	if(boxMovement != NULL){
		os << boxMovement->toString() << std::endl;
	}
}

void GameState::printConvertedMovements(std::ostream & os){
	if(robotMovement != NULL){
		os << robotMovement->toConvertedString(dims.first) << std::endl;
	}

	if(boxMovement != NULL){
		os << boxMovement->toConvertedString(dims.first) << std::endl;
	}
}

void GameState::optimizeMovements(point lastPusher, point lastPuller){
	RobotMovement* rM = robotMovement;
	RobotMovement* last = NULL;

	//merge movements
	while(rM != NULL && rM->next != NULL){
		if(rM->type == rM->next->type){
			RobotMovement* temp = rM;
			rM = rM->next;
			temp->next = NULL;
			delete temp;
		}
		else if(last != NULL){
			last->next = rM;
			last = rM;
			rM = rM->next;
		}
		else if(last == NULL){
			robotMovement = rM;
			last = rM;
			rM = rM->next;
		}


	}

	rM = robotMovement;
	last= NULL;

	//remove non movements
	while(rM!= NULL){
		if(rM->type == RPUSHER && lastPusher != rM->dest){
			lastPusher = rM->dest;
			last = rM;
			rM = rM->next;
		}
		else if(rM->type ==RPULLER && lastPuller != rM->dest){
			lastPuller = rM->dest;
			last= rM;
			rM = rM->next;
		}
		else{
			if(last == NULL){
				robotMovement = rM->next;
				RobotMovement* temp = rM;
				rM = rM->next;
				temp->next = NULL;
				delete temp;
			}
			else{
				RobotMovement* temp = rM;
				rM = rM->next;
				last->next = rM;
				temp->next = NULL;
				delete temp;
			}
		}

	}

}
