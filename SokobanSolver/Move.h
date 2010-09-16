/*
 * Move.h
 *
 *  Created on: Sep 15, 2010
 *      Author: rohrmann
 */

#ifndef MOVE_H_
#define MOVE_H_

#include <utility>
#include <iostream>

class AbstractGameMap;

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

	virtual bool doMove(AbstractGameMap & map) = 0;
	virtual bool undoMove(AbstractGameMap& map) = 0;

	bool undoMoves(AbstractGameMap& map, Move* until);
	bool doMoves(AbstractGameMap& map, Move* from);

	virtual std::pair<int,int> source()const = 0;
	virtual std::pair<int,int> destination()const = 0;
	virtual bool changedHash() = 0;

	int getLevel()const{
		return level;
	}

	Move* getPrev()const{
		return prev;
	}

	Move* setPrev(Move* prev);
	Move* removePrev();

	Move* findCommonAncestor(Move* prev);

	int getCosts() const{
		return level + estimatedCosts;
	}

	int getEstimatedCosts() const{
		return estimatedCosts;
	}

	virtual void print(std::ostream & stream){
		stream << "Move from:" << "("<<source().first <<","<<source().second << ")" << " to (" << destination().first << "," << destination().second << ")" ;
	}

	virtual int getBot()const =0;
};

#endif /* MOVE_H_ */
