/*
 * AbstractGameMap.h
 *
 *  Created on: Sep 15, 2010
 *      Author: rohrmann
 */

#ifndef ABSTRACTGAMEMAP_H_
#define ABSTRACTGAMEMAP_H_

#include "AbstractMap.h"
#include <vector>
#include "HashValue.h"
#include "defs.h"
#include <list>

class Move;
template<int L>
class ZobristHashing;

class AbstractGameMap : public AbstractMap {
protected:
	HashValue<ZOBRISTHASHLENGTH> hash;
	ZobristHashing<ZOBRISTHASHLENGTH>* hashing;
public:
	AbstractGameMap();
	virtual ~AbstractGameMap();

	virtual std::pair<int,int> getBot(int botNum) const =0;
	virtual void setBot(int botNum, std::pair<int,int>) =0;
	virtual Robot getType(int botNum)const = 0;
	virtual unsigned int getBotSize()const =0;

	virtual const std::vector<std::pair<int,int> >& getTargets()const = 0;

	virtual const HashValue<ZOBRISTHASHLENGTH>& getHash()=0;

	void set(int x, int y, Field type) throw(InvalidArgumentException);
	void set(std::pair<int,int> coords, Field type) throw(InvalidArgumentException){
		set(coords.first, coords.second,type);
	}

	virtual const HashValue<ZOBRISTHASHLENGTH>& initHash() = 0;
	virtual const std::vector<std::pair<int,int> >& getComponents() = 0;
	virtual const std::vector<std::pair<int,int> >& getRobotComponents() =0;

	virtual bool solved(){
		for(std::vector<std::pair<int,int> >::const_iterator it = getTargets().begin(); it != getTargets().end(); ++it){
			if(this->at(*it) != BOX)
				return false;
		}

		return true;
	}

	virtual std::list<Move* > possibleMoves(Move* prev)=0;

	virtual int estimatedCost(std::pair<int,int> coord) =0;
};

#endif /* ABSTRACTGAMEMAP_H_ */
