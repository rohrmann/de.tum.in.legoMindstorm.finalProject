/*
 * BoxMove.h
 *
 *  Created on: Sep 15, 2010
 *      Author: rohrmann
 */

#ifndef BOXMOVE_H_
#define BOXMOVE_H_

#include "Move.h"
#include <utility>

template<int L>
class ZobristHashing;

class BoxMove: public Move {
public:
	std::pair<int,int> sourceBox;
	std::pair<int,int> destBox;
	std::pair<int,int> sourceBot;
	std::pair<int,int> destBot;
	unsigned int botNum;

	BoxMove(std::pair<int,int> sourceBox, std::pair<int,int> destBox,std::pair<int,int> sourceBot, std::pair<int,int> destBot, unsigned int botNum);
	BoxMove(std::pair<int,int> sourceBox, std::pair<int,int> destBox,std::pair<int,int> sourceBot, std::pair<int,int> destBot, unsigned int botNum, Move* prev, int estimatedCosts);
	virtual ~BoxMove();

	bool doMove(GameMap& map,const ZobristHashing<HASHLENGTH>& hashing);
	bool undoMove(GameMap & map,const ZobristHashing<HASHLENGTH>& hashing);
};

#endif /* BOXMOVE_H_ */
