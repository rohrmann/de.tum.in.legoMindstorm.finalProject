/*
 * Move.h
 *
 *  Created on: Sep 15, 2010
 *      Author: rohrmann
 */

#ifndef MOVE_H_
#define MOVE_H_

#include "defs.h"

class GameMap;

template<int L>
class ZobristHashing;

class Move {
protected:
	Move* prev;
	int level;
	int children;
	int estimatedCosts;
public:
	Move();
	Move(Move * prev,int estimatedCosts);
	virtual ~Move();

	virtual bool doMove(GameMap& map,const ZobristHashing<HASHLENGTH>& hashing);
	virtual bool undoMove(GameMap& map, const ZobristHashing<HASHLENGTH>& hashing);

	bool undoMoves(GameMap& map,const ZobristHashing<HASHLENGTH>& hashing, Move* until);
	bool doMoves(GameMap& map,const ZobristHashing<HASHLENGTH>& hashing, Move* from);

	int getLevel()const{
		return level;
	}

	Move* getPrev()const{
		return prev;
	}

	int getChildren() const{
		return children;
	}

	Move *removePrev();

	Move *findSurvivor();

	Move* setPrev(Move* prev);

	Move* findCommonAncestor(Move* prev);

	int getCosts() const{
		return level + estimatedCosts;
	}

	int getEstimatedCosts() const{
		return estimatedCosts;
	}

	void setEstimatedCosts(int estimatedCosts){
		this->estimatedCosts = estimatedCosts;
	}

	static int counter;
};

#endif /* MOVE_H_ */
