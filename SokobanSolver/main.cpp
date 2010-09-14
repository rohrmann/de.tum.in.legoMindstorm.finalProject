/*
 * main.cpp
 *
 *  Created on: Sep 14, 2010
 *      Author: rohrmann
 */


#include <iostream>
#include <fstream>
#include "ArrayMap.h"
#include "ArrayMapUtils.h"
using namespace std;

int main(int argc, char ** argv){

	ifstream ifs("sokoban.xsb",ios::in);
	ArrayMapUtils* util = new ArrayMapUtils();
	AbstractMap* map =  util->read(ifs);
	util->write(*map,cout);

	return 0;

}
