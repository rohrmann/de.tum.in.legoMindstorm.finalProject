/*
 * ArrayMap.cpp
 *
 *  Created on: Sep 14, 2010
 *      Author: rohrmann
 */

#include "ArrayMap.h"
#include "ZobristHashing.h"
#include <utility>
#include <stack>
#include "BoxMove.h"

Field& ArrayMap::get(int x, int y) throw(InvalidArgumentException){
	if(!validCoords(x,y)){
		throw InvalidArgumentException("ArrayMap::get invalid coords");
	}

	return map[x*dims.second+y];
}

void ArrayMap::set(int x, int y, Field type) throw(InvalidArgumentException){

	AbstractGameMap::set(x,y,type);

	map[x*dims.second+y] = type;

	componentsChanged = true;

	for(unsigned int i =0;i < targets.size();i++){
		std::pair<int,int> target = targets[i];
		if(target.first==x && target.second == y){
			targetsChanged=true;
			break;
		}
	}
}

std::pair<int,int> ArrayMap::dimensions()const{
	return dims;
}

ArrayMap::ArrayMap(){
	dims = std::pair<int,int>(0,0);
	components = NULL;
	componentsChanged = true;
	estimatedCostMap = NULL;
	targetsChanged = true;
}

ArrayMap::~ArrayMap(){
	delete [] map;
	delete [] components;
	delete [] estimatedCostMap;
}


bool ArrayMap::contains(int x, int y) const{
	if(at(x,y) == UNDEFINED)
		return false;

	return true;
}

Field ArrayMap::at(int x, int y) const{
	if(!validCoords(x,y))
		return UNDEFINED;

	return map[x * dims.second + y ];
}

bool ArrayMap::validCoords(int x, int y) const{
	return 0<=x && 0 <= y && x< dims.first && y < dims.second;
}

std::pair<int,int> ArrayMap::getBot(int botNum) const{
	return bots[botNum];
}

void ArrayMap::setBot(int botNum,std::pair<int,int> coords){
	bots[botNum] =coords;
}

Robot ArrayMap::getType(int botNum) const{
	return types[botNum];
}

const HashValue<ZOBRISTHASHLENGTH>& ArrayMap::initHash(){
	hash = 0;
	for(int i =0;i< dims.first;i++){
		for(int j =0; j< dims.second; j++){
			if(map[i*dims.second + j] == BOX || map[i*dims.second+j]== EMPTY){
				hash ^= hashing->getField(i,j,map[i*dims.second + j]);
			}
		}
	}

	std::vector<std::pair<int,int> > botComponents = getRobotComponents();

	for(unsigned int i =0; i < botComponents.size();i++){
		hash ^= hashing->getBot(botComponents[i],i);
	}

	return hash;
}

void ArrayMap::updateComponents(){
	if(components == NULL){
		components = new int[dims.first*dims.second];
	}

	for(unsigned int i =0 ; i< botComponents.size();i++){
		hash ^= hashing->getBot(botComponents[i],i);
	}

	for(int i =0; i <dims.first;i++){
		for(int j=0; j< dims.second;j++){
			components[i*dims.second+j] = -1;
		}
	}

	int counter = 0;
	for(int i =0; i< dims.first;i++){
		for(int j =0; j< dims.second;j++){
			if(components[i*dims.second+j] == -1 && ( map[i*dims.second+j]==EMPTY)){
				dfs(i,j,counter++);
			}
		}
	}

	int component = -1;
	componentIDs.clear();
	for(int i =0; i < dims.first;i++){
		for(int j =0; j< dims.second;j++){
			if(components[i*dims.second + j] > component){
				component = components[i*dims.second+j];
				componentIDs.push_back(std::make_pair(i,j));
			}
		}
	}

	botComponents.clear();

	for(unsigned int i =0 ;i < bots.size();i++){
		botComponents.push_back(componentIDs[components[bots[i].first*dims.second + bots[i].second]]);
		hash^= hashing->getBot(botComponents[i],i);
 	}

	componentsChanged = false;
}

const HashValue<ZOBRISTHASHLENGTH>& ArrayMap::getHash(){
	if(componentsChanged)
		updateComponents();

	return hash;
}

void ArrayMap::dfs(int x, int y, int component){
	std::stack<std::pair<int,int> > stack;

	stack.push(std::make_pair(x,y));

	while(!stack.empty()){
		std::pair<int,int> field = stack.top();
		stack.pop();

		components[field.first*dims.second + field.second] = component;


		if(validCoords(field.first,field.second+1)&& components[field.first*dims.second+ field.second+1]== -1 &&( map[field.first*dims.second + field.second+1]==EMPTY)){
			stack.push(std::make_pair(field.first,field.second+1));
		}

		if(validCoords(field.first,field.second-1)&& components[field.first*dims.second+ field.second-1]== -1 &&(map[field.first*dims.second + field.second-1]==EMPTY)){
			stack.push(std::make_pair(field.first,field.second-1));
		}

		if(validCoords(field.first+1,field.second)&& components[(field.first+1)*dims.second+ field.second]== -1 &&( map[(field.first+1)*dims.second + field.second]==EMPTY)){
					stack.push(std::make_pair(field.first+1,field.second));
		}

		if(validCoords(field.first-1,field.second) && components[(field.first-1)*dims.second+ field.second]== -1 &&( map[(field.first-1)*dims.second + field.second]==EMPTY)){
					stack.push(std::make_pair(field.first-1,field.second));
		}
	}
}

void ArrayMap::clipToReachable(){
	bool * field = new bool[dims.first*dims.second];

	for(int i =0; i< dims.first*dims.second;i++){
		field[i] = false;
	}

	for(unsigned int i=0;i < bots.size();i++){
		std::stack<std::pair<int,int> > stack;

		stack.push(bots[i]);
		field[bots[i].first*dims.second + bots[i].second] = true;

		while(!stack.empty()){
			std::pair<int,int> cur = stack.top();
			stack.pop();

			if(validCoords(cur.first,cur.second+1) && map[cur.first*dims.second + cur.second+1] != UNDEFINED && field[cur.first*dims.second+cur.second+1]==false){
				field[cur.first*dims.second+cur.second+1] = true;
				if(map[cur.first*dims.second + cur.second+1] == BOX || map[cur.first*dims.second+cur.second+1]==EMPTY){
					stack.push(std::make_pair(cur.first,cur.second+1));
				}
			}

			if(validCoords(cur.first,cur.second-1) && map[cur.first*dims.second + cur.second-1] != UNDEFINED && field[cur.first*dims.second+cur.second-1]==false){
							field[cur.first*dims.second+cur.second-1] = true;
							if(map[cur.first*dims.second + cur.second-1] == BOX || map[cur.first*dims.second+cur.second-1]==EMPTY){
								stack.push(std::make_pair(cur.first,cur.second-1));
				}
			}

			if(validCoords(cur.first+1,cur.second) && map[(cur.first+1)*dims.second + cur.second] != UNDEFINED && field[(cur.first+1)*dims.second+cur.second]==false){
							field[(cur.first+1)*dims.second+cur.second] = true;
							if(map[(cur.first+1)*dims.second + cur.second] == BOX || map[(cur.first+1)*dims.second+cur.second]==EMPTY){
								stack.push(std::make_pair(cur.first+1,cur.second));
				}
			}

			if(validCoords(cur.first-1,cur.second) && map[(cur.first-1)*dims.second + cur.second] != UNDEFINED && field[(cur.first-1)*dims.second+cur.second]==false){
							field[(cur.first-1)*dims.second+cur.second] = true;
							if(map[(cur.first-1)*dims.second + cur.second] == BOX || map[(cur.first-1)*dims.second+cur.second]==EMPTY){
								stack.push(std::make_pair(cur.first-1,cur.second));
				}
			}

		}
	}

	for(int i=0; i<dims.first*dims.second;i++){
		if(!field[i])
			map[i] = UNDEFINED;
	}
}

std::list<Move*> ArrayMap::possibleMoves(Move* prev){
	std::list<Move* > result;
	std::stack<std::pair<int,int> > stack;
	Robot type;
	char * helper = new char[dims.first*dims.second];

	for(unsigned int i =0; i < this->bots.size();i++){
		stack.push(bots[i]);
		type = types[i];

		for(int j =0 ;j < dims.first*dims.second;j++){
			helper[j] = 0;
		}

		while(!stack.empty()){
			std::pair<int,int> pos = stack.top();
			stack.pop();

			helper[pos.first*dims.second + pos.second] = 1;

			if(at(pos.first,pos.second+1)==BOX){
				if(type == PUSHER && fieldIsAccessible(pos.first,pos.second+2,i)){
					Move *move = new BoxMove(std::make_pair(pos.first,pos.second+1),std::make_pair(pos.first,pos.second+2),bots[i],std::make_pair(pos.first,pos.second+1),i,prev,estimatedCost(std::make_pair(pos.first,pos.second+2)));
					result.push_back(move);
				}
				else if(type ==PULLER && fieldIsAccessible(pos.first,pos.second-1,i)){
					Move *move = new BoxMove(std::make_pair(pos.first,pos.second+1),pos,bots[i],std::make_pair(pos.first,pos.second-1),i,prev,estimatedCost(pos));
					result.push_back(move);
				}
			}
			else if(fieldIsAccessible(pos.first,pos.second+1,i) && helper[pos.first*dims.second+pos.second+1] == 0){
				stack.push(std::make_pair(pos.first,pos.second+1));
			}


			if(at(pos.first,pos.second-1)==BOX){
				if(type == PUSHER && fieldIsAccessible(pos.first,pos.second-2,i)){
					Move *move = new BoxMove(std::make_pair(pos.first,pos.second-1),std::make_pair(pos.first,pos.second-2),bots[i],std::make_pair(pos.first,pos.second-1),i,prev,estimatedCost(std::make_pair(pos.first,pos.second-2)));
					result.push_back(move);
				}
				else if(type ==PULLER && fieldIsAccessible(pos.first,pos.second+1,i)){
					Move *move = new BoxMove(std::make_pair(pos.first,pos.second-1),pos,bots[i],std::make_pair(pos.first,pos.second+1),i,prev,estimatedCost(pos));
					result.push_back(move);
				}
			}
			else if(fieldIsAccessible(pos.first,pos.second-1,i) && helper[pos.first*dims.second+pos.second-1] == 0){
				stack.push(std::make_pair(pos.first,pos.second-1));
			}


			if(at(pos.first+1,pos.second)==BOX){
				if(type == PUSHER && fieldIsAccessible(pos.first+2,pos.second,i)){
					Move *move = new BoxMove(std::make_pair(pos.first+1,pos.second),std::make_pair(pos.first+2,pos.second),bots[i],std::make_pair(pos.first+1,pos.second),i,prev,estimatedCost(std::make_pair(pos.first+2,pos.second)));
					result.push_back(move);
				}
				else if(type ==PULLER && fieldIsAccessible(pos.first-1,pos.second,i)){
					Move *move = new BoxMove(std::make_pair(pos.first+1,pos.second),pos,bots[i],std::make_pair(pos.first-1,pos.second),i,prev,estimatedCost(pos));
					result.push_back(move);
				}
			}
			else if(fieldIsAccessible(pos.first+1,pos.second,i) && helper[(pos.first+1)*dims.second+pos.second] == 0){
				stack.push(std::make_pair(pos.first+1,pos.second));
			}


			if(at(pos.first-1,pos.second)==BOX){
				if(type == PUSHER && fieldIsAccessible(pos.first-2,pos.second,i)){
					Move *move = new BoxMove(std::make_pair(pos.first-1,pos.second),std::make_pair(pos.first-2,pos.second),bots[i],std::make_pair(pos.first-1,pos.second),i,prev,estimatedCost(std::make_pair(pos.first-2,pos.second)));
					result.push_back(move);
				}
				else if(type ==PULLER && fieldIsAccessible(pos.first+1,pos.second,i)){
					Move *move = new BoxMove(std::make_pair(pos.first-1,pos.second),pos,bots[i],std::make_pair(pos.first+1,pos.second),i,prev,estimatedCost(pos));
					result.push_back(move);
				}
			}
			else if(fieldIsAccessible(pos.first-1,pos.second,i) && helper[(pos.first-1)*dims.second+pos.second] == 0){
				stack.push(std::make_pair(pos.first-1,pos.second));
			}

		}
	}

	return result;
}

bool ArrayMap::fieldIsAccessible(int x, int y, int robot){
	for(unsigned int i = 0; i< bots.size();i++){
		if(robot != i && bots[i].first == x && bots[i].second == y)
			return false;
	}

	return at(x,y) == EMPTY;
}

int ArrayMap::estimatedCost(std::pair<int,int> coord){
	if(targetsChanged){
		updateEstimatedCosts();
		targetsChanged = false;
	}

	return estimatedCostMap[coord.first*dims.second + coord.second];
}

void ArrayMap::updateEstimatedCosts(){
	if(estimatedCostMap == NULL){
		estimatedCostMap = new int[dims.first*dims.second];
	}

	std::vector<std::pair<int,int> > possibleTargets;

	for(unsigned int i =0; i< targets.size();i++){
		if(at(targets[i])!=BOX)
			possibleTargets.push_back(targets[i]);
	}

	for(int i = 0; i < dims.first; i++){
		for(int j =0; j< dims.second;j++){
			if(at(i,j)==BOX || at(i,j) == EMPTY){
				std::pair<int,int> cur = std::make_pair(i,j);
				int distance = getDistance(cur,possibleTargets[0]);

				for(unsigned int k=1; k < possibleTargets.size();k++){
					if(distance > getDistance(cur,possibleTargets[k])){
						distance = getDistance(cur,possibleTargets[k]);
					}
				}

				estimatedCostMap[i*dims.second+j] = distance;
			}
		}
	}
}

int ArrayMap::getDistance(std::pair<int,int> a, std::pair<int,int> b){
	return abs(a.first-b.first)+abs(a.second-b.second);
}

