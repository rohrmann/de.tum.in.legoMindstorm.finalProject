/*
 * MapUtils.h
 *
 *  Created on: Sep 14, 2010
 *      Author: rohrmann
 */

#ifndef MAPUTILS_H_
#define MAPUTILS_H_

#include <iostream>
#include "XSB.h"
#include "defs.h"
#include <iomanip>

class PrintableMap;
class GameState;

template<int L>
class ZobristHashing;

class MapUtils{
public:
	static PrintableMap* read( std::istream& input);
	static bool write(const PrintableMap& map, std::ostream& output);
	static PrintableMap* convert(const GameState& map);
	static void printMap(const GameState& map, std::ostream & output);
	static char xsb2Ch(XSB xsb);
	static XSB ch2XSB(char ch);
	static GameState* buildGameState(const PrintableMap& map, const ZobristHashing<HASHLENGTH>& hashing);


	template<typename T>
	static void printMap(T*field, std::pair<dimension,dimension> dims){
		for(dimension i=0;i<dims.first;i++){
			for(dimension j =0; j< dims.second;j++){
				std::cout <<std::setw(4)<< (int)field[i*dims.second+j];
			}
			std::cout << std::endl;
		}
	}
};




#endif /* MAPUTILS_H_ */
