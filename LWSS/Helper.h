/*
 * Helper.h
 *
 *  Created on: Sep 14, 2010
 *      Author: rohrmann
 */

#ifndef HELPER_H_
#define HELPER_H_

#include <string>

namespace std{
	template<typename T, typename U>
	class pair;
}

class Helper {
public:

	static std::string pair2Str(std::pair<int,int> pair);

	static std::string pair2Str(int x,int y);

	static std::pair<int,int> north(std::pair<int,int> origin, int dist=1);
	static std::pair<int,int> south(std::pair<int,int> origin,int dist =1);
	static std::pair<int,int> west(std::pair<int,int> origin, int dist =1);
	static std::pair<int,int> east(std::pair<int,int> origin, int dist = 1);

};

#endif /* HELPER_H_ */
