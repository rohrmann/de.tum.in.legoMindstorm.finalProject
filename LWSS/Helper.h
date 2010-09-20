/*
 * Helper.h
 *
 *  Created on: Sep 14, 2010
 *      Author: rohrmann
 */

#ifndef HELPER_H_
#define HELPER_H_

#include <string>
#include "defs.h"

namespace std{
	template<typename T, typename U>
	class pair;
}

class Helper {
public:

	static std::string pair2Str(point pair);

	static std::string pair2Str(dimension x,dimension y);

	static point north(point origin, int dist=1);
	static point south(point origin,int dist =1);
	static point west(point origin, int dist =1);
	static point east(point origin, int dist = 1);

};

#endif /* HELPER_H_ */
