/*
 * MapAlgorithms.h
 *
 *  Created on: Sep 19, 2010
 *      Author: rohrmann
 */

#ifndef MAPALGORITHMS_H_
#define MAPALGORITHMS_H_

#include <stack>
#include "defs.h"
#include "Helper.h"

template<typename T, typename U>
point dfs(point coords, T* field, T value, U tester){
	std::stack<point > stack;

	field[tester.addr(coords)] = value;
	stack.push(coords);

	point topLeft = coords;

	while(!stack.empty()){
		point pos = stack.top();
		stack.pop();

		if(pos.first < topLeft.first || (pos.first == topLeft.first && pos.second < topLeft.second)){
			topLeft= pos;
		}

		if(tester(Helper::north(pos)) && field[tester.addr(Helper::north(pos))]!=value){
			field[tester.addr(Helper::north(pos))] = value;
			stack.push(Helper::north(pos));
		}

		if(tester(Helper::west(pos))&& field[tester.addr(Helper::west(pos))]!= value){
			field[tester.addr(Helper::west(pos))]=value;
			stack.push(Helper::west(pos));
		}

		if(tester(Helper::south(pos))&& field[tester.addr(Helper::south(pos))]!= value){
			field[tester.addr(Helper::south(pos))]=value;
			stack.push(Helper::south(pos));
		}

		if(tester(Helper::east(pos))&& field[tester.addr(Helper::east(pos))]!= value){
			field[tester.addr(Helper::east(pos))]=value;
			stack.push(Helper::east(pos));
		}
	}
	return topLeft;
}


int calcManhattanDist(point a, point b);



#endif /* MAPALGORITHMS_H_ */
