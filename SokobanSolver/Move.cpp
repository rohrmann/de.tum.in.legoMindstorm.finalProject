/*
 * Move.cpp
 *
 *  Created on: Sep 15, 2010
 *      Author: rohrmann
 */

#include "Move.h"
#include "AbstractGameMap.h"
#include <utility>
#include <stack>

Move::Move() {
	prev = NULL;
	level = 0;
	children = 0;
	estimatedCosts=0;
}

Move::Move(Move* prev,int estimatedCosts){
	this->prev = prev;
	if(prev!= NULL){
		level = prev->getLevel()+1;
		prev->children++;
	}
	else
		level = 0;
	this->estimatedCosts = estimatedCosts;
}

Move::~Move() {
	if(prev->children==1){
		delete prev;
	}
	else
		prev->children--;
}

Move* Move::setPrev(Move* prev){
	this->prev = prev;
	this->level = prev->getLevel()+1;
	prev->children++;

	return this;
}

Move* Move::removePrev(){
	prev->children--;
	this->level = 0;
	Move* temp = prev;
	prev = NULL;

	return temp;
}

Move* Move::findCommonAncestor(Move * move){
	Move* left = this;
	Move* right = move;

	while(left->getLevel() > right->getLevel()){
		left = left->getPrev();
	}

	while(right->getLevel() > left->getLevel()){
		right= right->getPrev();
	}

	while(left != right){
		left = left->getPrev();
		right = right->getPrev();
	}

	return left;
}

bool Move::undoMoves(AbstractGameMap & map, Move* until){
	Move* current = this;

	while(current != NULL && current != until){
		current->undoMove(map);
		current = current->getPrev();
	}

	return current != NULL ? true:false;
}

bool Move::doMoves(AbstractGameMap & map, Move* from){
	std::stack<Move*> moves;

	Move* current=this;

	while(current != NULL && current != from){
		moves.push(current);
		current = current->getPrev();
	}

	if(current == NULL)
		return false;

	while(!moves.empty()){
		moves.top()->doMove(map);
		moves.pop();
	}

	return true;

}
