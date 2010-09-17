/*
 * ArrayMap.h
 *
 *  Created on: Sep 16, 2010
 *      Author: rohrmann
 */

#ifndef ARRAYMAP_H_
#define ARRAYMAP_H_

#include "Game.h"
#include <unordered_set>
#include "HashValue.h"
#include "PairHasher.h"
#include "PairEqual.h"
#include "defs.h"
#include "Map.h"
#include "Robot.h"

namespace std{
	template<typename T, typename U>
	class pair;

}

template<int L>
class ZobristHashing;

class GameMap : public Map<Game> {
private:
	void clipToAccessibleArea();
	template<typename U,typename T>
	void dfs(std::pair<int,int> coords, T* data, T value, T notVisited);
	std::pair<int,int> * getBotComponents();
public:
	std::pair<int,int>* bots;
	Robot* types;
	unsigned int numBots;

	std::pair<int,int>* targets;
	std::unordered_set< std::pair<int,int>, PairHasher, PairEqual > targetsHash;
	unsigned int numTargets;

	HashValue<HASHLENGTH> hashValue;

	GameMap();
	GameMap(std::pair<int,int>* bots, Robot* types,unsigned int numBots, std::pair<int,int>* targets,unsigned int numTargets,Game* map, std::pair<int,int> dims);
	~GameMap();

	void init(const ZobristHashing<HASHLENGTH> & hashing);

	void setBot(unsigned int botNum, std::pair<int,int> coords){
		bots[botNum] = coords;
	}

	void setBot(unsigned int botNum, int x, int y){
		setBot(botNum,std::make_pair(x,y));
	}

	Game getField(int x, int y)const;
	Game getField(std::pair<int,int> p)const{
		return getField(p.first,p.second);
	}

	HashValue<HASHLENGTH> getHash(const ZobristHashing<HASHLENGTH>& hashing);

	bool solved();

};

#endif /* ARRAYMAP_H_ */
