/*
 * ArrayMap.h
 *
 *  Created on: Sep 16, 2010
 *      Author: rohrmann
 */

#ifndef GAMESTATE_H_
#define GAMESTATE_H_

#include <unordered_set>
#include <list>
#include "HashValue.h"
#include "PairHasher.h"
#include "PairEqual.h"
#include "defs.h"
#include "Map.h"
#include "Robot.h"
#include "Command.h"

namespace std{
	template<typename T, typename U>
	class pair;

}

template<int L>
class ZobristHashing;

class GameState : public Map<field> {
public:
	field componentValue;
	int level;
	int estimatedCosts;
	GameState* prev;
	unsigned int children;

	point puller;
	point pullerTL;
	point pusher;
	point pusherTL;

	Command *commands;

	static point* targets;
	static std::unordered_set< point, PairHasher, PairEqual > targetsHash;
	static unsigned int numTargets;

	static int instanceCounter;

	point* boxes;
	unsigned int numBoxes;

	HashValue<HASHLENGTH> hashValue;

	GameState();
	GameState(field* map, point dims);
	GameState(point puller,point pullerTL,point pusher,point pusherTL,point* boxes, unsigned int numBoxes,int estimatedCosts,field componentValue,GameState* prev,field* map,point dims);
	~GameState();

	void init(point puller,point pullerTL,point pusher,point pusherTL,point* boxes, unsigned int numBoxes,int estimatedCosts,field componentValue,GameState* prev);

	bool solved();

	int getCosts(){
		return level + estimatedCosts;
	}

	HashValue<HASHLENGTH> getHashValue(ZobristHashing<HASHLENGTH>* hashing);

	void setPrev(GameState* state);

	void updateTL();
	void updateComponents();

	void moveBot(Robot type, point pos);
	void movePuller(point pos);
	void movePusher(point pos);
	void moveBox(unsigned int boxNum,point pos);

	GameState* copy();

	void calcEstimatedCosts();

	unsigned char getFingerPrint(point coords);

	field getNextComponentValue();

};

#endif /* ARRAYMAP_H_ */
