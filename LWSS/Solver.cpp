/*
 * Solver.cpp
 *
 *  Created on: Sep 16, 2010
 *      Author: rohrmann
 */

#include "Solver.h"
#include "GameState.h"
#include "PrintableMap.h"
#include "MapUtils.h"
#include "Helper.h"
#include "ZobristHashing.h"
#include "FlowTester.h"
#include "MapAlgorithms.h"
#include "BoxMovement.h"
#include "RobotMovement.h"

Solver::Solver(const PrintableMap& map){
	hashing = new ZobristHashing<HASHLENGTH>(map.dims,2);
	initialState = MapUtils::buildGameState(map,*hashing);
	initialState->children++;

	initConnections();
}

Solver::~Solver() {
	delete hashing;
	delete initialState;
}

void Solver::initConnections(){
	field * map = new field[9];
	bool * accessible = new bool[9];
	point dims = make_point(3,3);
	unsigned char result=0;

	FlowTester tester(accessible,dims);

	for(unsigned int i =0;i < 256;i++){

		for(unsigned int j =0;j < 9;j++){
			map[j] = 0;
			accessible[j] = true;
		}

		accessible[4] = false;

		for(unsigned int j = 0; j < 4;j++){
			if(((i>>j)&1)==1){
				accessible[j] = false;
			}
		}
		for(unsigned int j = 4; j < 8;j++){
			if(((i>>j)&1)==1){
				accessible[j+1] = false;
			}
		}
		field component = 1;

		//north
		if(accessible[1] && map[1] == 0){
			dfs(make_point(0,1),map,component++,tester);
		}

		//west
		if(accessible[3] && map[3] == 0){
			dfs(make_point(1,0),map,component++,tester);
		}

		//east
		if(accessible[5]&&map[5] == 0){
			dfs(make_point(1,2),map,component++,tester);
		}

		//south
		if(accessible[7]&& map[7] == 0){
			dfs(make_point(2,1),map,component++,tester);
		}


		result = 0;

		//north-west
		if(map[1] != 0 && map[3] != 0 && map[1] != map[3] ){
			result |= NW;
		}

		//west-south
		if(map[3] != 0 && map[7]!= 0 && map[3] != map[7]){
			result |=WS;
		}

		//south-east
		if(map[7] != 0 && map[5] != 0 && map[7]!=map[5]){
			result |=SE;
		}

		//east-north
		if(map[5] != 0 && map[1] != 0 && map[5] != map[1]){
			result |= EN;
		}

		//west-east
		if(map[5] != 0 && map[3] != 0 && map[3] != map[5]){
			result |= WE;
		}

		if(map[1] != 0 && map[7] != 0 && map[1] != map[7]){
			result |= NS;
		}

		connections[i] = result;

	}
}


void Solver::solve(){

/*	RobotMovement* movements = initialState->path(RPULLER,make_point(2,5),10);

	while(movements!= NULL){
		std::cout << (movements->type == RPULLER? "Puller:" : "Pusher:") << Helper::pair2Str(movements->dest) << std::endl;
		movements = movements->next;
	}*/

	queue.push(initialState);

	while(!queue.empty()){
		currentState = queue.top();
		queue.pop();

		//std::cout << "Next map with costs:"<< currentState->getCosts() << std::endl;
		//MapUtils::printMap(*currentState,std::cout);

		//std::cout << "Level:" << currentState->level << " EstimatedCosts:" << currentState->estimatedCosts << " Costs:" <<currentState->getCosts() << std::endl;

		if(currentState->solved()){
			std::stack<GameState*> solution;

			while(currentState != NULL){
				solution.push(currentState);
				currentState = currentState->prev;
			}

			while(!solution.empty()){
				currentState = solution.top();
				solution.pop();


				currentState->printConvertedMovements(std::cout);
				MapUtils::printMap(*currentState,std::cout);
			}

			exit(0);
		}

		HashValue<HASHLENGTH> hashValue = currentState->getHashValue(hashing);

		if(pruning.find(hashValue) == pruning.end()){
			//std::cout << "next pruned map with costs:" << currentState->getCosts() << std::endl;
			//MapUtils::printMap(*currentState,std::cout);
			pruning.insert(hashValue);
			findNextStates();
		}

		deleteBranch();

	}

}

unsigned int Solver::findNextStates(){
	unsigned int numberNewStates = 0;
	for(unsigned int i = 0; i< currentState->numBoxes; i++){
		point pos = currentState->boxes[i];

		//north
		if(currentState->at(Helper::north(pos)) != 0){
			if(currentState->at(Helper::south(pos))!= 0 && currentState->at(currentState->pusher)==currentState->at(Helper::south(pos))){
				GameState * tempState = currentState->copy();
				tempState->moveBox(i,Helper::north(pos));
				BoxMovement movement(i,pos,NORTH,RPUSHER);
				generateMovements(tempState,movement);

				delete tempState;

			}

			if(currentState->at(Helper::north(pos,2))!=0 && currentState->at(currentState->puller) == currentState->at(Helper::north(pos))){
				GameState * tempState = currentState->copy();
				tempState->moveBox(i,Helper::north(pos));
				BoxMovement movement(i,pos,NORTH,RPULLER);
				generateMovements(tempState,movement);

				delete tempState;
			}
		}

		//west
		if(currentState->at(Helper::west(pos)) != 0 ){
			if(currentState->at(Helper::east(pos))!= 0 && currentState->at(currentState->pusher)==currentState->at(Helper::east(pos))){
				GameState * tempState = currentState->copy();
				tempState->moveBox(i,Helper::west(pos));
				BoxMovement movement(i,pos,WEST,RPUSHER);
				generateMovements(tempState,movement);

				delete tempState;
			}

			if(currentState->at(Helper::west(pos,2))!=0 && currentState->at(currentState->puller) == currentState->at(Helper::west(pos))){
				GameState * tempState = currentState->copy();
				tempState->moveBox(i,Helper::west(pos));
				BoxMovement movement(i,pos,WEST,RPULLER);
				generateMovements(tempState,movement);

				delete tempState;
			}
		}

		//east
		if(currentState->at(Helper::east(pos)) != 0 ){
			if(currentState->at(Helper::west(pos))!= 0 && currentState->at(currentState->pusher)==currentState->at(Helper::west(pos))){
				GameState * tempState = currentState->copy();
				tempState->moveBox(i,Helper::east(pos));
				BoxMovement movement(i,pos,EAST,RPUSHER);
				generateMovements(tempState,movement);

				delete tempState;
			}

			if(currentState->at(Helper::east(pos,2))!=0 && currentState->at(currentState->puller) == currentState->at(Helper::east(pos))){
				GameState * tempState = currentState->copy();
				tempState->moveBox(i,Helper::east(pos));
				BoxMovement movement(i,pos,EAST,RPULLER);
				generateMovements(tempState,movement);

				delete tempState;
			}
		}

		//south
		if(currentState->at(Helper::south(pos)) != 0 ){
			if(currentState->at(Helper::north(pos))!= 0 && currentState->at(currentState->pusher)==currentState->at(Helper::north(pos))){
				GameState * tempState = currentState->copy();
				tempState->moveBox(i,Helper::south(pos));
				BoxMovement movement(i,pos,SOUTH,RPUSHER);
				generateMovements(tempState,movement);

				delete tempState;
			}

			if(currentState->at(Helper::south(pos,2))!=0 && currentState->at(currentState->puller) == currentState->at(Helper::south(pos))){
				GameState * tempState = currentState->copy();
				tempState->moveBox(i,Helper::south(pos));
				BoxMovement movement(i,pos,SOUTH,RPULLER);
				generateMovements(tempState,movement);

				delete tempState;
			}
		}

/*
		//north pusher
		if( currentState->at(Helper::north(pos)) != 0 && currentState->at(Helper::south(pos))!=0 && !collision(RPUSHER,pos,Helper::north(pos),NORTH)){
			pusher = true;
		}

		//north puller
		if(currentState->at(Helper::north(pos))!= 0 && currentState->at(Helper::north(pos,2))!= 0 && !collision(RPULLER,pos,Helper::north(pos),NORTH)){
			puller = true;
		}

		if(pusher || puller){
			GameState* newState = currentState->copy();

			moveBoxNorth(newState,i,pos,Helper::north(pos));
			newState->setPrev(currentState);

			//decide which bot to take
			if(pusher && !puller){
				newState->moveBot(RPUSHER,pos,hashing);
			}
			else if(puller && !pusher){
				newState->moveBot(RPULLER,Helper::north(pos,2),hashing);
			}
			else{
				if(newState->at(pos) != newState->at(Helper::north(pos,2))){
					GameState* parallelState = newState->copy();
					parallelState->moveBot(RPULLER,Helper::north(pos,2),hashing);
					queue.push(parallelState);
				}


				newState->moveBot(RPUSHER,pos,hashing);

				queue.push(newState);
			}
		}*/

	}
//	std::cout << numberNewStates << std::endl;
	return numberNewStates;
}

bool Solver::insertState(GameState* newState){
	newState->calcEstimatedCosts();
	newState->setPrev(currentState);
	queue.push(newState);

	//std::cout<<"Possible new state with costs:" << newState->getCosts() << std::endl;
	//MapUtils::printMap(*newState,std::cout);
	return true;
}

void Solver::deleteBranch(){
	GameState* temp=currentState;

	while(currentState != NULL && currentState->children==0){
		temp = currentState;
		currentState = currentState->prev;

		delete temp;
	}
}

void Solver::moveBoxNorth(GameState* state,unsigned int i, point pos, point newPos){
	field oldComponents[4];

	oldComponents[NORTH] = state->at(Helper::north(newPos));
	oldComponents[WEST] = state->at(Helper::west(newPos));
	oldComponents[SOUTH] = state->at(Helper::south(newPos));
	oldComponents[EAST] = state->at(Helper::east(newPos));

	state->set(newPos,0);

	//update south
	field componentValue = max(max(state->at(Helper::south(pos)),state->at(Helper::west(pos))),state->at(Helper::east(pos)));

	ComponentTester tester(state);

	dfs(pos,state->map,componentValue,tester);

	oldComponents[SOUTH] = componentValue;

	// collect 8 fields around box
	unsigned int index = state->getFingerPrint(newPos);
	unsigned char connection = connections[index];

	//update west
	if((connection & WS) && oldComponents[WEST]==state->at(Helper::west(newPos)) && oldComponents[WEST] == oldComponents[SOUTH]){
		componentValue = state->getNextComponentValue();

		if(componentValue == 0)
			return;

		dfs(Helper::west(newPos),state->map,componentValue,tester);
		oldComponents[WEST] = componentValue;
	}

	//update east
	if((connection&SE)&& oldComponents[EAST] == state->at(Helper::east(newPos)) && (oldComponents[SOUTH] == oldComponents[EAST] || oldComponents[WEST] == oldComponents[EAST])){
		componentValue = state->getNextComponentValue();

		if(componentValue == 0)
			return;

		dfs(Helper::east(newPos),state->map,componentValue,tester);
		oldComponents[EAST] = componentValue;
	}

	//update north
	if((connection & NS) && oldComponents[NORTH] == state->at(Helper::north(newPos)) && (oldComponents[SOUTH] == oldComponents[NORTH] || oldComponents[WEST] == oldComponents[NORTH] || oldComponents[EAST] == oldComponents[NORTH])){
		componentValue = state->getNextComponentValue();

		if(componentValue==0)
			return;

		dfs(Helper::north(newPos),state->map,componentValue,tester);
		oldComponents[NORTH] = componentValue;
	}

	state->boxes[i] = newPos;
	state->hashValue ^= hashing->getBox(pos);
	state->hashValue ^= hashing->getBox(newPos);
}

void Solver::generateMovements(GameState* tempState,const BoxMovement& movement){
	unsigned int numComponents =0;
	field components[4];
	point evasionFields[4];
	unsigned int numEvasionFields = 0;
	std::unordered_set<point, PointHasher, PointEqual> nodes;

	tempState->findAdjacentComponents(movement.dest(),numComponents,components);

	currentState->bfs(movement.bot == RPUSHER? currentState->pusher:currentState->puller,movement.srcBot(),nodes);

	nodes.insert(movement.destBot());
	nodes.insert(movement.dest());
	nodes.insert(movement.src());

	currentState->findEvasionFields(movement.bot == RPUSHER? currentState->puller: currentState->pusher,numComponents, components, nodes, tempState,numEvasionFields,evasionFields);

	for(unsigned int i = 0; i< numEvasionFields; i++){
		nodes.clear();
		RobotMovement* rMove = currentState->path(movement.bot == RPUSHER?RPULLER:RPUSHER,evasionFields[i],nodes,MAXELEMENTS);

		if(rMove != NULL){
			GameState* newState = currentState->copy();
			newState->apply(rMove);

			//MapUtils::printMap(*newState,std::cout);

			nodes.insert(movement.dest());
			nodes.insert(movement.destBot());

			rMove = newState->path(movement.bot,movement.srcBot(),nodes,MAXELEMENTS);

			if(rMove == NULL){
				delete newState;
				continue;
			}

			newState->apply(rMove);
			//MapUtils::printMap(*newState,std::cout);
			newState->apply(new BoxMovement(movement));

			insertState(newState);
		}
	}
}
