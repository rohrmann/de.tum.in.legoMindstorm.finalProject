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

	queue.push(initialState);

	while(!queue.empty()){
		currentState = queue.top();
		queue.pop();

		//MapUtils::printMap(*currentState,std::cout);
		//MapUtils::printMap(currentState->map,currentState->dims);
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

				MapUtils::printMap(*currentState,std::cout);
				std::cout << currentState->hashValue << std::endl;
			}

			std::cout << "Instances:" << GameState::instanceCounter << std::endl;
			exit(0);
		}

		//if(pruning.find(currentState->hashValue) == pruning.end()){
			pruning.insert(currentState->hashValue);
			if(findNextStates()==0){
				deleteBranch();
			}
		//}
		//else
		//	deleteBranch();

	}

}

unsigned int Solver::findNextStates(){
	bool pusher = false;
	bool puller = false;
	unsigned int numberNewStates = 0;
	for(unsigned int i = 0; i< currentState->numBoxes; i++){
		point pos = currentState->boxes[i];
		pusher = false;
		puller = false;

		//north
		if(currentState->at(Helper::north(pos)) != 0){
			if(currentState->at(Helper::south(pos))!= 0 && currentState->at(currentState->pusher)==currentState->at(Helper::south(pos))){
				GameState * newState = currentState->copy();
				newState->moveBox(i,Helper::north(pos),hashing);
				newState->moveBot(RPUSHER,pos,hashing);
				newState->calcEstimatedCosts();

				if(insertState(newState))
					numberNewStates++;

			}
			else if(currentState->at(Helper::north(pos,2))!=0 && currentState->at(currentState->puller) == currentState->at(Helper::north(pos))){
				GameState * newState = currentState->copy();
				newState->moveBox(i,Helper::north(pos),hashing);
				newState->moveBot(RPULLER,Helper::north(pos,2),hashing);
				newState->calcEstimatedCosts();

				if(insertState(newState))
					numberNewStates++;
			}
		}

		//west
		if(currentState->at(Helper::west(pos)) != 0 ){
			if(currentState->at(Helper::east(pos))!= 0 && currentState->at(currentState->pusher)==currentState->at(Helper::east(pos))){
				GameState * newState = currentState->copy();
				newState->moveBox(i,Helper::west(pos),hashing);
				newState->moveBot(RPUSHER,pos,hashing);
				newState->calcEstimatedCosts();

				if(insertState(newState))
					numberNewStates++;
			}
			else if(currentState->at(Helper::west(pos,2))!=0 && currentState->at(currentState->puller) == currentState->at(Helper::north(pos))){
				GameState * newState = currentState->copy();
				newState->moveBox(i,Helper::west(pos),hashing);
				newState->moveBot(RPULLER,Helper::west(pos,2),hashing);
				newState->calcEstimatedCosts();

				if(insertState(newState))
					numberNewStates++;
			}
		}

		//east
		if(currentState->at(Helper::east(pos)) != 0 ){
			if(currentState->at(Helper::west(pos))!= 0 && currentState->at(currentState->pusher)==currentState->at(Helper::west(pos))){
				GameState * newState = currentState->copy();
				newState->moveBox(i,Helper::east(pos),hashing);
				newState->moveBot(RPUSHER,pos,hashing);
				newState->calcEstimatedCosts();

				if(insertState(newState))
					numberNewStates++;
			}
			else if(currentState->at(Helper::east(pos,2))!=0 && currentState->at(currentState->puller) == currentState->at(Helper::east(pos))){
				GameState * newState = currentState->copy();
				newState->moveBox(i,Helper::east(pos),hashing);
				newState->moveBot(RPULLER,Helper::east(pos,2),hashing);
				newState->calcEstimatedCosts();

				if(insertState(newState))
					numberNewStates++;
			}
		}

		//south
		if(currentState->at(Helper::south(pos)) != 0 ){
			if(currentState->at(Helper::north(pos))!= 0 && currentState->at(currentState->pusher)==currentState->at(Helper::north(pos))){
				GameState * newState = currentState->copy();
				newState->moveBox(i,Helper::south(pos),hashing);
				newState->moveBot(RPUSHER,pos,hashing);
				newState->calcEstimatedCosts();

				if(insertState(newState))
					numberNewStates++;
			}
			else if(currentState->at(Helper::south(pos,2))!=0 && currentState->at(currentState->puller) == currentState->at(Helper::south(pos))){
				GameState * newState = currentState->copy();
				newState->moveBox(i,Helper::south(pos),hashing);
				newState->moveBot(RPULLER,Helper::south(pos,2),hashing);
				newState->calcEstimatedCosts();

				if(insertState(newState))
					numberNewStates++;
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
	newState->setPrev(currentState);
	queue.push(newState);
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

bool Solver::collision(Robot type,point boxStart, point boxEnd,Direction dir){
	if(type == RPUSHER){
		point puller = currentState->puller;
		point botStart;

		switch(dir){
		case NORTH:
			botStart = Helper::south(boxStart);
			return puller.second == botStart.second && puller.first >= boxEnd.first && puller.first <= botStart.first;
		case WEST:
			botStart = Helper::east(boxStart);
			return puller.first == botStart.first && puller.second >= boxEnd.second && puller.second <= botStart.second;
		case EAST:
			botStart = Helper::west(boxStart);
			return puller.first == botStart.first && puller.second <= boxEnd.second && puller.second >= botStart.second;
		case SOUTH:
			botStart = Helper::north(boxStart);
			return puller.second == botStart.second && puller.first >= botStart.first && puller.first <= boxEnd.first;
		}
	}
	else{
		point pusher = currentState->pusher;
		point botEnd;

		switch(dir){
		case NORTH:
			botEnd = Helper::north(boxEnd);
			return pusher.second == boxStart.second && pusher.first <= boxStart.first && pusher.first >= botEnd.first;
		case SOUTH:
			botEnd = Helper::south(boxEnd);
			return pusher.second == boxStart.second && pusher.first <= botEnd.first && pusher.first >= boxStart.first;
		case WEST:
			botEnd = Helper::west(boxEnd);
			return pusher.first == boxStart.first && pusher.second <= boxStart.second && pusher.second >= botEnd.second;
		case EAST:
			botEnd = Helper::east(boxEnd);
			return pusher.first == boxStart.first && pusher.second <= botEnd.second&& pusher.second >= boxStart.second;
		}
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
