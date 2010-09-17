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
#include "Game.h"

class PrintableMap;
class GameMap;

class MapUtils{
public:
	static PrintableMap* read( std::istream& input);
	static bool write(const PrintableMap& map, std::ostream& output);
	static PrintableMap* convert(const GameMap& map);
	static void printMap(const GameMap& map, std::ostream & output);
	static char xsb2Ch(XSB xsb);
	static XSB ch2XSB(char ch);
	static XSB game2XSB(Game field);
	static GameMap* buildGameMap(const PrintableMap& map);


	template<typename T>
	static void printMap(T*field, std::pair<int,int> dims){
		for(int i=0;i<dims.first;i++){
			for(int j =0; j< dims.second;j++){
				std::cout << field[i*dims.second+j];
			}
			std::cout << std::endl;
		}
	}
};


#endif /* MAPUTILS_H_ */
