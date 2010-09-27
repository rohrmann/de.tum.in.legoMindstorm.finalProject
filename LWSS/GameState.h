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
#include "PointHasher.h"
#include "PointEqual.h"
#include "defs.h"
#include "Map.h"
#include "Robot.h"
#include "RobotMovement.h"
#include "BoxMovement.h"

namespace std{
	template<typename T, typename U>
	class pair;

}

template<int L>
class ZobristHashing;

class RobotMovement;
class BoxMovement;

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

	RobotMovement* robotMovement;
	BoxMovement* boxMovement;

	static point* targets;
	static std::unordered_set< point, PointHasher, PointEqual > targetsHash;
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

	void apply(RobotMovement* movement);
	void apply(BoxMovement* movement);

	void calcEstimatedCosts();

	unsigned char getFingerPrint(point coords);

	field getNextComponentValue();

	bool bfs(point start, point dest,std::unordered_set<point, PointHasher, PointEqual>& nodes);

	bool findEvasionField(point start, const std::unordered_set<point,PointHasher,PointEqual>& nodes, point& result);

	RobotMovement* path(Robot type, point dest,std::unordered_set<point,PointHasher,PointEqual>& excludedNodes, unsigned int maxElements);

	RobotMovement* recPathfinding(Robot type,point botA, point botB, point dest,std::unordered_set<point,PointHasher,PointEqual>& excludedNodes,unsigned int maxElements);


	void findAdjacentComponents(point point, unsigned int& numComponents, field* components);

	void findEvasionFields(point start, unsigned int numComponents, field* components,const std::unordered_set<point,PointHasher,PointEqual>& nodes,GameState* tempState, unsigned int& numEvasionFields, point* evasionFields);

	void printMovements(std::ostream& os);
	void printConvertedMovements(std::ostream&os);

	void optimizeMovements(point lastPusher, point lastPuller);
};

#endif /* ARRAYMAP_H_ */
