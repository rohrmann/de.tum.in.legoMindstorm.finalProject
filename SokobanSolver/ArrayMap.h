/*
 * ArrayMap.hpp
 *
 *  Created on: Sep 14, 2010
 *      Author: rohrmann
 */

#ifndef ARRAYMAP_HPP_
#define ARRAYMAP_HPP_

#include "AbstractGameMap.h"


class ArrayMap : public AbstractGameMap {
private:
	Field* map;
	int* components;
	int* estimatedCostMap;
	std::vector<std::pair<int,int> > botComponents;
	std::vector<std::pair<int,int> > componentIDs;
	std::pair<int,int> dims;
	std::vector<std::pair<int,int> >bots;
	std::vector<Robot> types;
	std::vector<std::pair<int,int> > targets;
	bool componentsChanged;
	bool targetsChanged;
	ArrayMap();
public:

	~ArrayMap();

	Field at(int x, int y) const;
	Field at(std::pair<int,int> coord)const {
		return at(coord.first,coord.second);
	}
	Field& get(int x, int y) throw(InvalidArgumentException);
	void set(int x,int y, Field type) throw(InvalidArgumentException);

	std::pair<int,int> dimensions() const ;

	std::pair<int,int> getBot(int botNum) const;
	void setBot(int botNum, std::pair<int,int> coords);
	Robot getType(int botNum) const;
	unsigned int getBotSize()const{
		return bots.size();
	}

	bool contains(int x, int y)const;
	bool validCoords(int x, int y)const;

	const std::vector<std::pair<int,int> >& getTargets()const{
		return targets;
	}

	const HashValue<ZOBRISTHASHLENGTH>& initHash();
	const HashValue<ZOBRISTHASHLENGTH>& getHash();

	const std::vector<std::pair<int,int> >& getComponents(){
		if(componentsChanged){
			updateComponents();
		}
		return componentIDs;
	}

	const std::vector<std::pair<int,int>  >& getRobotComponents(){
		if(componentsChanged){
			updateComponents();
		}
		return botComponents;
	}

	void updateComponents();

	void dfs(int x, int y, int component);

	void clipToReachable();

	friend class ArrayMapFactory;

	std::list<Move*> possibleMoves(Move* lastMove);

	int estimatedCost(std::pair<int,int> coord);

	void updateEstimatedCosts();

	int getDistance(std::pair<int,int> a, std::pair<int,int> b);

	bool fieldIsAccessible(int x, int y, int robot);


};

#endif /* ARRAYMAP_HPP_ */
