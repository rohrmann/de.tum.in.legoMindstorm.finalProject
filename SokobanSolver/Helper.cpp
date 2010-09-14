/*
 * Helper.cpp
 *
 *  Created on: Sep 14, 2010
 *      Author: rohrmann
 */

#include "Helper.h"

Helper::Helper() {
	// TODO Auto-generated constructor stub

}

Helper::~Helper() {
	// TODO Auto-generated destructor stub
}

Field Helper::ch2Field(char ch){
	switch(ch){
	case '#':
		return WALL;
	case ' ':
		return EMPTY;
	case '@':
		return PUSHER;
	case '!':
		return PULLER;
	case '.':
		return TARGET;
	case '*':
		return BOXONTARGET;
	case '$':
		return BOX;
	default:
		return UNDEFINED;
	}
}

char Helper::field2Ch(Field field){
	switch(field){
	case WALL:
		return '#';
	case EMPTY:
		return ' ';
	case PUSHER:
		return '@';
	case PULLER:
		return '!';
	case TARGET:
		return '.';
	case BOXONTARGET:
		return '*';
	case BOX:
		return '$';
	default:
		return ' ';
	}
}
