/*
 * BoxMove.h
 *
 *  Created on: Sep 15, 2010
 *      Author: rohrmann
 */

#ifndef BOXMOVE_H_
#define BOXMOVE_H_

#include "Move.h"
#include "Robot.h"

class BoxMove: public Move {
private:
	std::pair<int,int> sourceBox;
	std::pair<int,int> destBox;
	std::pair<int,int> sourceBot;
	std::pair<int,int> destBot;
	int botNum;
public:
	BoxMove(std::pair<int,int> sourceBox, std::pair<int,int> destBox,std::pair<int,int> sourceBot, std::pair<int,int> destBot, int botNum, Move* prev, int estimatedCosts);
	virtual ~BoxMove();

	std::pair<int,int> source() const{
		return sourceBox;
	}

	std::pair<int,int> destination() const{
		return destBox;
	}

	bool doMove(AbstractGameMap& map);
	bool undoMove(AbstractGameMap & map);

	bool changedHash(){
		return true;
	}

	void print(std::ostream& os){
		Move::print(os);
		os << std::endl;
		os << "Robot from:(" << sourceBot.first << "," << sourceBot.second << ") to (" << destBot.first << "," << destBot.second << ")" << std::endl;
		os << "Costs:"  << getCosts() << " Level:"  << getLevel() << " EstimatedCosts:" << getEstimatedCosts();
	}

	int getBot() const{
		return botNum;
	}
};

#endif /* BOXMOVE_H_ */
