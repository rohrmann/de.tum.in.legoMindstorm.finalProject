/*
 * defs.h
 *
 *  Created on: Sep 15, 2010
 *      Author: rohrmann
 */

#ifndef DEFS_H_
#define DEFS_H_

#include <utility>

#define HASHLENGTH 64

#define MAXELEMENTS 10

typedef unsigned char field;
typedef unsigned char dimension;
typedef std::pair<dimension,dimension> point;

point make_point(dimension x, dimension y);

enum Direction{
	NORTH=0,WEST=1,SOUTH=2,EAST=3
};

enum Connection{
	NW=1,WS=2,SE=4,EN=8,NS=16,WE=32
};

template<typename T>
T max(T a,T b){
	return a > b? a :b;
}


#endif /* DEFS_H_ */
