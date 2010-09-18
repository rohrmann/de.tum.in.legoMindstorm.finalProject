/*
 * Solver.cpp
 *
 *  Created on: Sep 16, 2010
 *      Author: rohrmann
 */

#include "Solver.h"
#include "GameMap.h"
#include <queue>
#include <stack>
#include "Move.h"
#include "HashValueEqual.h"
#include "HashValueHasher.h"
#include "MoveComparator.h"
#include "MapUtils.h"
#include "Helper.h"
#include "BoxMove.h"

Solver::~Solver() {
}


void Solver::solve(){
	std::unordered_set< HashValue<HASHLENGTH>, HashValueHasher<HASHLENGTH>,HashValueEqual<HASHLENGTH> > pruning;
	std::priority_queue<Move*,std::vector<Move*>, MoveComparator> q;
	std::vector<Move*> moves;

	Move* startMove= new Move();
	Move* lastMove= startMove;
	q.push(startMove);

	while(!q.empty()){
		Move* nextMove = q.top();
		q.pop();

		Move* ancestor = nextMove->findCommonAncestor(lastMove);
		lastMove->undoMoves(*map,*hashing,ancestor);
		nextMove->doMoves(*map,*hashing,ancestor);
		lastMove = nextMove;


		if(map->solved()){
			lastMove->undoMoves(*map,*hashing,startMove);

			std::stack<Move*> solution;
			while(lastMove != startMove){
				solution.push(lastMove);
				lastMove = lastMove->getPrev();
			}

			MapUtils::printMap(*map,std::cout);

			while(!solution.empty()){
				Move* move = solution.top();
				solution.pop();

				move->doMove(*map,*hashing);
				MapUtils::printMap(*map,std::cout);
			}

			exit(0);
		}

		HashValue<HASHLENGTH> hashValue = map->getHash(*hashing);

		if(pruning.find(hashValue) == pruning.end()){
			pruning.insert(hashValue);

			moves.clear();
			findPossibleMoves(moves,lastMove);

			if(moves.size() == 0){
				lastMove = deleteBranch(lastMove);
			}

			for(std::vector<Move*>::iterator it = moves.begin(); it != moves.end(); ++it){
				q.push(*it);
			}
		}
		else{
			lastMove = deleteBranch(lastMove);
		}
	}
}

Move* Solver::deleteBranch(Move* move){
	Move* current = move;
	Move* temp;
	while(current != NULL && current->getChildren() == 0){
		current->undoMove(*map,*hashing);
		temp = current;
		current = current->getPrev();
		delete temp;
	}

	return current;
}

void Solver::findPossibleMoves(std::vector<Move *, std::allocator<Move*> >& result, Move* lastMove){
	std::queue<std::pair<int,int> > queue;
	std::pair<int,int>* prev = new std::pair<int,int>[map->dims.first*map->dims.second];
	bool * visited = new bool[map->dims.first*map->dims.second];

	for(unsigned int k = 0; k< map->numBots; k++){
		for(int i =0; i < map->dims.first; i++){
			for(int j =0; j< map->dims.second;j++){
				prev[map->addr(i,j)] = std::make_pair(i,j);
				visited[map->addr(i,j)]=false;
			}
		}

		queue.push(map->bots[k]);
		visited[map->addr(map->bots[k])] = true;

		if(map->types[k] == RPUSHER){
			while(!queue.empty()){
				std::pair<int,int> pos = queue.front();
				queue.pop();

				if(map->getField(Helper::north(pos))==GBOX && map->getField(Helper::north(pos,2))==GEMPTY){
					std::unordered_set<std::pair<int,int>, PairHasher, PairEqual> nodes;
					nodes.insert(Helper::north(pos));
					nodes.insert(Helper::north(pos,2));
					insertPath(pos,prev,nodes);

					if(!checkCollision(k,nodes)){
						BoxMove* move = new BoxMove(Helper::north(pos),Helper::north(pos,2),map->bots[k],Helper::north(pos),k,lastMove,0);
						result.push_back(move);
					}
				}
				else if(map->getField(Helper::north(pos))==GEMPTY && !visited[map->addr(Helper::north(pos))]){
					visited[map->addr(Helper::north(pos))] = true;
					prev[map->addr(Helper::north(pos))] = pos;
					queue.push(Helper::north(pos));
				}

				if(map->getField(Helper::west(pos))==GBOX && map->getField(Helper::west(pos,2))==GEMPTY){
					std::unordered_set<std::pair<int,int>, PairHasher, PairEqual> nodes;
					nodes.insert(Helper::west(pos));
					nodes.insert(Helper::west(pos,2));
					insertPath(pos,prev,nodes);

					if(!checkCollision(k,nodes)){
						BoxMove* move = new BoxMove(Helper::west(pos),Helper::west(pos,2),map->bots[k],Helper::west(pos),k,lastMove,0);
						result.push_back(move);
					}
				}
				else if(map->getField(Helper::west(pos))==GEMPTY && !visited[map->addr(Helper::west(pos))]){
					visited[map->addr(Helper::west(pos))] = true;
					prev[map->addr(Helper::west(pos))] = pos;
					queue.push(Helper::west(pos));
				}

				if(map->getField(Helper::south(pos))==GBOX && map->getField(Helper::south(pos,2))==GEMPTY){
					std::unordered_set<std::pair<int,int>, PairHasher, PairEqual> nodes;
					nodes.insert(Helper::south(pos));
					nodes.insert(Helper::south(pos,2));
					insertPath(pos,prev,nodes);

					if(!checkCollision(k,nodes)){
						BoxMove* move = new BoxMove(Helper::south(pos),Helper::south(pos,2),map->bots[k],Helper::south(pos),k,lastMove,0);
						result.push_back(move);
					}
				}
				else if(map->getField(Helper::south(pos))==GEMPTY && !visited[map->addr(Helper::south(pos))]){
					visited[map->addr(Helper::south(pos))] = true;
					prev[map->addr(Helper::south(pos))] = pos;
					queue.push(Helper::south(pos));
				}

				if(map->getField(Helper::east(pos))==GBOX && map->getField(Helper::east(pos,2))==GEMPTY){
					std::unordered_set<std::pair<int,int>, PairHasher, PairEqual> nodes;
					nodes.insert(Helper::east(pos));
					nodes.insert(Helper::east(pos,2));
					insertPath(pos,prev,nodes);

					if(!checkCollision(k,nodes)){
						BoxMove* move = new BoxMove(Helper::east(pos),Helper::east(pos,2),map->bots[k],Helper::east(pos),k,lastMove,0);
						result.push_back(move);
					}
				}
				else if(map->getField(Helper::east(pos))==GEMPTY && !visited[map->addr(Helper::east(pos))]){
					visited[map->addr(Helper::east(pos))] = true;
					prev[map->addr(Helper::east(pos))] = pos;
					queue.push(Helper::east(pos));
				}

			}
		}
		else{
			while(!queue.empty()){
				std::pair<int,int> pos = queue.front();
				queue.pop();

				if(map->getField(Helper::north(pos))==GBOX && map->getField(Helper::south(pos))==GEMPTY){
					std::unordered_set<std::pair<int,int>, PairHasher, PairEqual> nodes;
					nodes.insert(Helper::north(pos));
					nodes.insert(Helper::south(pos));
					insertPath(pos,prev,nodes);

					if(!checkCollision(k,nodes)){
						BoxMove* move= new BoxMove(Helper::north(pos),pos,map->bots[k],Helper::south(pos),k,lastMove,0);
						result.push_back(move);
					}
				}
				else if(map->getField(Helper::north(pos))==GEMPTY && !visited[map->addr(Helper::north(pos))]){
					visited[map->addr(Helper::north(pos))] = true;
					prev[map->addr(Helper::north(pos))] = pos;
					queue.push(Helper::north(pos));
				}

				if(map->getField(Helper::west(pos))==GBOX && map->getField(Helper::east(pos))==GEMPTY){

					std::unordered_set<std::pair<int,int>, PairHasher, PairEqual> nodes;
					nodes.insert(Helper::west(pos));
					nodes.insert(Helper::east(pos));
					insertPath(pos,prev,nodes);

					if(!checkCollision(k,nodes)){
						BoxMove* move = new BoxMove(Helper::west(pos),pos,map->bots[k],Helper::east(pos),k,lastMove,0);
						result.push_back(move);
					}
				}
				else if(map->getField(Helper::west(pos))==GEMPTY && !visited[map->addr(Helper::west(pos))]){
					visited[map->addr(Helper::west(pos))] = true;
					prev[map->addr(Helper::west(pos))] = pos;
					queue.push(Helper::west(pos));
				}

				if(map->getField(Helper::south(pos))==GBOX && map->getField(Helper::north(pos))==GEMPTY){
					std::unordered_set<std::pair<int,int>, PairHasher, PairEqual> nodes;
					nodes.insert(Helper::north(pos));
					nodes.insert(Helper::south(pos));
					insertPath(pos,prev,nodes);

					if(!checkCollision(k,nodes)){
						BoxMove* move = new BoxMove(Helper::south(pos),pos,map->bots[k],Helper::north(pos),k,lastMove,0);
						result.push_back(move);
					}


				}
				else if(map->getField(Helper::south(pos))==GEMPTY && !visited[map->addr(Helper::south(pos))]){
					visited[map->addr(Helper::south(pos))] = true;
					prev[map->addr(Helper::south(pos))] = pos;
					queue.push(Helper::south(pos));
				}

				if(map->getField(Helper::east(pos))==GBOX && map->getField(Helper::west(pos))==GEMPTY){
					std::unordered_set<std::pair<int,int>, PairHasher, PairEqual> nodes;
					nodes.insert(Helper::west(pos));
					nodes.insert(Helper::east(pos));
					insertPath(pos,prev,nodes);

					if(!checkCollision(k,nodes)){
						BoxMove* move = new BoxMove(Helper::east(pos),pos,map->bots[k],Helper::west(pos),k,lastMove,0);
						result.push_back(move);
					}
				}
				else if(map->getField(Helper::east(pos))==GEMPTY && !visited[map->addr(Helper::east(pos))]){
					visited[map->addr(Helper::east(pos))] = true;
					prev[map->addr(Helper::east(pos))] = pos;
					queue.push(Helper::east(pos));
				}
			}
		}
	}

	for(std::vector<Move*>::iterator it = result.begin(); it != result.end();++it){
		(*it)->setEstimatedCosts(calcEstimatedCosts(*it));
	}

	delete [] visited;
	delete [] prev;
}

bool Solver::checkCollision(unsigned int botNum, const std::unordered_set<std::pair<int,int>, PairHasher, PairEqual>& nodes){
	for(unsigned int k = 0; k < map->numBots;k++){
		if(k != botNum){
			if(nodes.find(map->bots[k]) != nodes.end())
				return true;
		}
	}

	return false;
}

void Solver::insertPath(std::pair<int,int> pos, std::pair<int,int>* prev, std::unordered_set<std::pair<int,int>, PairHasher, PairEqual>& nodes){
	std::pair<int,int> current= pos;

	while(current != prev[map->addr(current)]){
		nodes.insert(current);
		current = prev[map->addr(current)];
	}

	nodes.insert(current);
}

int Solver::calcEstimatedCosts(Move* move){
	int result = 0;
	move->doMove(*map,*hashing);
	result = calcEstimatedCosts();
	move->undoMove(*map,*hashing);

	return result;
}

int Solver::calcEstimatedCosts(){
	int result = 0;
	for(int i =0; i< map->dims.first;i++){
		for(int j =0; j< map->dims.second;j++){
			if(map->getField(i,j)==GBOX){
				if(map->targetsHash.find(std::make_pair(i,j)) == map->targetsHash.end()){
					int min = 10000000;
					for(unsigned int k =0; k < map->numTargets;k++){
						if(map->getField(map->targets[k]) != GBOX && min > getManhattanDist(i,j,map->targets[k])){
							min = getManhattanDist(i,j,map->targets[k]);
						}
					}
					result += min;
				}
			}
		}
	}

	return result;
}

int Solver::getManhattanDist(int a, int b, int x, int y){
	return abs(a-x)+abs(b-y);
}
