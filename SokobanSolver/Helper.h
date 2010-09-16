/*
 * Helper.h
 *
 *  Created on: Sep 14, 2010
 *      Author: rohrmann
 */

#ifndef HELPER_H_
#define HELPER_H_

#include "Field.h"
#include <utility>
#include <iostream>
#include <sstream>

class Helper {
public:
	Helper();
	virtual ~Helper();

	static Field ch2Field(char ch);
	static char field2Ch(Field field);

	template<typename T>
	static void printMap(T*field, std::pair<int,int> dims){
		for(int i=0;i<dims.first;i++){
			for(int j =0; j< dims.second;j++){
				std::cout << field[i*dims.second+j];
			}
			std::cout << std::endl;
		}
	}

	static std::string pair2Str(std::pair<int,int> pair){
		std::stringstream ss;

		ss<< "(" << pair.first << "," << pair.second << ")";

		return ss.str();
	}
};

#endif /* HELPER_H_ */
