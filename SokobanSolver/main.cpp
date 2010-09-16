/*
 * main.cpp
 *
 *  Created on: Sep 14, 2010
 *      Author: rohrmann
 */


#include <iostream>
#include <fstream>
#include "PrintableMap.h"
#include "MapUtils.h"
#include "ArrayMapFactory.h"
#include "ArrayMap.h"
#include "Move.h"
#include "BoxMove.h"
#include "Solver.h"
using namespace std;

int main(int argc, char ** argv){

	ifstream ifs("level0001.xsb",ios::in);

	PrintableMap* map = MapUtils::read(ifs);
	ArrayMapFactory factory;
	ArrayMap * game = factory.createMap(*map);
	delete map;

	Solver solver;

	solver.solve(*game);

/*	std::cout << game->getHash() << std::endl;

	MapUtils::printMap(*game,cout);

	for(int i =0; i< game->getRobotComponents().size();i++){
		std::cout << game->getRobotComponents().at(i).first << ";" << game->getRobotComponents().at(i).second  << std::endl;	}

	game->setBot(0,std::make_pair<int,int>(1,7));

	MapUtils::printMap(*game,cout);

	for(int i =0; i< game->getRobotComponents().size();i++){
			std::cout << game->getRobotComponents().at(i).first << ";" << game->getRobotComponents().at(i).second  << std::endl;
		}

	game->updateComponents();

	for(int i =0; i< game->getRobotComponents().size();i++){
		std::cout << game->getRobotComponents().at(i).first << ";" << game->getRobotComponents().at(i).second  << std::endl;
		}

	std::cout<< game->getHash() << std::endl;*/

//	MapUtils::printMap(*game,cout);
	/*Move* move1 = new BoxMove(make_pair(6,3),make_pair(6,4),make_pair(5,1),make_pair(6,3),PUSHER,NULL,0);
	Move* move2 = new BoxMove(make_pair(7,3),make_pair(6,3),make_pair(6,3),make_pair(7,3),PUSHER,NULL,0);
	move2->setPrev(move1);
	Move* move3 = new BoxMove(make_pair(3,6),make_pair(2,6),make_pair(6,3),make_pair(3,6),PUSHER,NULL,0);
	move3->setPrev(move1);

	Move* move4 = new BoxMove(make_pair(6,4),make_pair(6,3),make_pair(8,5),make_pair(6,2),PULLER,NULL,0);
	move4->setPrev(move3);

	move1->doMove(*game);
	std::cout << game->getHash() << std::endl;
	move2->doMove(*game);
	std::cout << game->getHash() << std::endl;

	Move* ancestor = move3->findCommonAncestor(move2);
	move2->undoMoves(*game,ancestor);
	std::cout << game->getHash() << std::endl;

	move3->doMoves(*game,ancestor);
	std::cout << game->getHash() << std::endl;

	move4->doMove(*game);
	std::cout << game->getHash() << std::endl;

	move4->undoMoves(*game,ancestor);
	std::cout << game->getHash() << std::endl;*/

	return 0;

}
