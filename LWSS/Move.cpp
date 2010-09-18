/*
 * Move.cpp
 *
 *  Created on: Sep 15, 2010
 *      Author: rohrmann
 */

#include "Move.h"
#include "GameMap.h"
#include "ZobristHashing.h"
#include <stack>

int Move::counter = 0;

Move::Move() {
	prev = NULL;
	level = 0;
	children = 0;
	estimatedCosts=0;

	counter++;
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
	this->children=0;

	counter++;
}

Move::~Move() {
	if(prev != NULL)
		prev->children--;

	counter--;
}

Move* Move::setPrev(Move* prev){
	if(prev != NULL){
		prev->children--;
	}

	this->prev = prev;
	this->level = prev->getLevel()+1;
	prev->children++;

	return this;
}

Move* Move::removePrev(){
	if(prev != NULL){
		prev->children--;
	}

	Move* result = prev;
	prev = NULL;
	level = 0;

	return result;
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

bool Move::undoMoves(GameMap& map,const ZobristHashing<HASHLENGTH>& hashing, Move* until){
	Move* current = this;

	while(current != NULL && current != until){
		current->undoMove(map,hashing);
		current = current->getPrev();
	}

	return current != NULL ? true:false;
}

bool Move::doMoves(GameMap & map,const ZobristHashing<HASHLENGTH>& hashing, Move* from){
	std::stack<Move*> moves;

	Move* current=this;

	while(current != NULL && current != from){
		moves.push(current);
		current = current->getPrev();
	}

	if(current == NULL)
		return false;

	while(!moves.empty()){
		moves.top()->doMove(map,hashing);
		moves.pop();
	}

	return true;

}

bool Move::doMove(GameMap& map, const ZobristHashing<HASHLENGTH>& hashing){
	return true;
}

bool Move::undoMove(GameMap& map, const ZobristHashing<HASHLENGTH>& hashing){
	return true;
}
