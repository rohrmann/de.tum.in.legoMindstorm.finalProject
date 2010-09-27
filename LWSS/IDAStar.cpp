/*
 * Solver.cpp
 *
 *  Created on: Sep 16, 2010
 *      Author: rohrmann
 */

#include "IDAStar.h"
#include "GameState.h"
#include "PrintableMap.h"
#include "MapUtils.h"
#include "Helper.h"
#include "ZobristHashing.h"
#include "FlowTester.h"
#include "MapAlgorithms.h"
#include "BoxMovement.h"
#include "RobotMovement.h"

IDAStar::IDAStar(const PrintableMap& map){
	hashing = new ZobristHashing<HASHLENGTH>(map.dims,2);
	initialState = MapUtils::buildGameState(map,*hashing);
	initialState->children++;

}

IDAStar::~IDAStar() {
	delete hashing;
	delete initialState;
}



void IDAStar::solve(int startCosts,  int increment){
	 int costBoundary = startCosts;

	while(true){
		std::cout<< "Cost Boundary:"  << costBoundary << std::endl;
		pruning.clear();
		recSolve(initialState,costBoundary);
		costBoundary += increment;
	}
}

void IDAStar::recSolve(GameState* currentState, int costBoundary){
	if(currentState->solved()){
		std::stack<GameState*> solution;

		while(currentState != NULL){
			solution.push(currentState);
			currentState = currentState->prev;
		}

		point pusher = currentState->pusher;
		point puller = currentState->puller;
		MapUtils::printMap(*solution.top(),std::cout);

		std::queue<GameState*> queue;

		std::cout << "Commands:" << std::endl;
		while(!solution.empty()){
			currentState = solution.top();
			solution.pop();

			currentState->optimizeMovements(pusher,puller);

			pusher = currentState->pusher;
			puller = currentState->puller;
			queue.push(currentState);
			//currentState->printConvertedMovements(std::cout);
		}

		currentState = queue.front();
		GameState* nextState= NULL;
		queue.pop();
		while(!queue.empty()){
			nextState = queue.front();
			queue.pop();
			if(nextState->robotMovement==NULL && nextState->boxMovement->bot == currentState->boxMovement->bot && currentState->boxMovement->nextDest() == nextState->boxMovement->dest()){
				currentState->boxMovement->incDist();
			}
			else{
				currentState->printConvertedMovements(std::cout);
				currentState = nextState;
			}
		}

		if(currentState != NULL){
			currentState->printConvertedMovements(std::cout);
		}

		exit(0);
	}

	HashValue<HASHLENGTH> hashValue = currentState->getHashValue(hashing);

	if(pruning.find(hashValue) == pruning.end() && currentState->getCosts() <= costBoundary){
		pruning.insert(hashValue);
		std::priority_queue<GameState*, std::vector<GameState*>,GameStateComparator> queue;
		findNextStates(currentState,queue);

		while(!queue.empty()){
			GameState* nextState = queue.top();
			queue.pop();

			recSolve(nextState, costBoundary);

			delete nextState;
		}
	}


}

unsigned int IDAStar::findNextStates(GameState* currentState,std::priority_queue<GameState*, std::vector<GameState*>, GameStateComparator>& queue){
	unsigned int numberNewStates = 0;
	for(unsigned int i = 0; i< currentState->numBoxes; i++){
		point pos = currentState->boxes[i];

		//north
		if(currentState->at(Helper::north(pos)) != 0){
			if(currentState->at(Helper::south(pos))!= 0 && currentState->at(currentState->pusher)==currentState->at(Helper::south(pos))){
				GameState * tempState = currentState->copy();
				tempState->moveBox(i,Helper::north(pos));
				BoxMovement movement(i,pos,NORTH,RPUSHER);
				generateMovements(currentState,tempState,movement,queue);

				delete tempState;

			}

			if(currentState->at(Helper::north(pos,2))!=0 && currentState->at(currentState->puller) == currentState->at(Helper::north(pos))){
				GameState * tempState = currentState->copy();
				tempState->moveBox(i,Helper::north(pos));
				BoxMovement movement(i,pos,NORTH,RPULLER);
				generateMovements(currentState,tempState,movement,queue);

				delete tempState;
			}
		}

		//west
		if(currentState->at(Helper::west(pos)) != 0 ){
			if(currentState->at(Helper::east(pos))!= 0 && currentState->at(currentState->pusher)==currentState->at(Helper::east(pos))){
				GameState * tempState = currentState->copy();
				tempState->moveBox(i,Helper::west(pos));
				BoxMovement movement(i,pos,WEST,RPUSHER);
				generateMovements(currentState,tempState,movement,queue);

				delete tempState;
			}

			if(currentState->at(Helper::west(pos,2))!=0 && currentState->at(currentState->puller) == currentState->at(Helper::west(pos))){
				GameState * tempState = currentState->copy();
				tempState->moveBox(i,Helper::west(pos));
				BoxMovement movement(i,pos,WEST,RPULLER);
				generateMovements(currentState,tempState,movement,queue);

				delete tempState;
			}
		}

		//east
		if(currentState->at(Helper::east(pos)) != 0 ){
			if(currentState->at(Helper::west(pos))!= 0 && currentState->at(currentState->pusher)==currentState->at(Helper::west(pos))){
				GameState * tempState = currentState->copy();
				tempState->moveBox(i,Helper::east(pos));
				BoxMovement movement(i,pos,EAST,RPUSHER);
				generateMovements(currentState,tempState,movement,queue);

				delete tempState;
			}

			if(currentState->at(Helper::east(pos,2))!=0 && currentState->at(currentState->puller) == currentState->at(Helper::east(pos))){
				GameState * tempState = currentState->copy();
				tempState->moveBox(i,Helper::east(pos));
				BoxMovement movement(i,pos,EAST,RPULLER);
				generateMovements(currentState,tempState,movement,queue);

				delete tempState;
			}
		}

		//south
		if(currentState->at(Helper::south(pos)) != 0 ){
			if(currentState->at(Helper::north(pos))!= 0 && currentState->at(currentState->pusher)==currentState->at(Helper::north(pos))){
				GameState * tempState = currentState->copy();
				tempState->moveBox(i,Helper::south(pos));
				BoxMovement movement(i,pos,SOUTH,RPUSHER);
				generateMovements(currentState,tempState,movement,queue);

				delete tempState;
			}

			if(currentState->at(Helper::south(pos,2))!=0 && currentState->at(currentState->puller) == currentState->at(Helper::south(pos))){
				GameState * tempState = currentState->copy();
				tempState->moveBox(i,Helper::south(pos));
				BoxMovement movement(i,pos,SOUTH,RPULLER);
				generateMovements(currentState,tempState,movement,queue);

				delete tempState;
			}
		}
	}
	return numberNewStates;
}

bool IDAStar::insertState(GameState* currentState,GameState* newState,std::priority_queue<GameState*, std::vector<GameState*>, GameStateComparator>&queue){
	newState->calcEstimatedCosts();
	newState->setPrev(currentState);
	queue.push(newState);

	//std::cout<<"Possible new state with costs:" << newState->getCosts() << std::endl;
	//MapUtils::printMap(*newState,std::cout);
	return true;
}

void IDAStar::generateMovements(GameState* currentState,GameState* tempState,const BoxMovement& movement, std::priority_queue<GameState*,std::vector<GameState*>,GameStateComparator>& queue){
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

			insertState(currentState,newState,queue);
		}
	}
}
