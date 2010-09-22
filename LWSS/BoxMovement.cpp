/*
 * BoxMovement.cpp
 *
 *  Created on: Sep 21, 2010
 *      Author: rohrmann
 */

#include "BoxMovement.h"
#include "GameState.h"
#include "Helper.h"
#include <sstream>

void BoxMovement::doMove(GameState* state){
	state->moveBox(boxNum,dest());
	state->moveBot(bot,destBot());
}

point BoxMovement::src()const{
	return source;
}

point BoxMovement::dest()const{
	switch(dir){
	case NORTH:
		return Helper::north(source);
	case SOUTH:
		return Helper::south(source);
	case WEST:
		return Helper::west(source);
	case EAST:
		return Helper::east(source);
	}
}

point BoxMovement::srcBot() const{
	switch(bot){
	case RPUSHER:
		switch(dir){
		case NORTH:
			return Helper::south(source);
		case WEST:
			return Helper::east(source);
		case EAST:
			return Helper::west(source);
		case SOUTH:
			return Helper::north(source);
		}
		break;
	case RPULLER:
		return dest();
		break;
	}
}

point BoxMovement::destBot() const{
	switch(bot){
	case RPUSHER:
		return source;
	case RPULLER:
		switch(dir){
		case NORTH:
			return Helper::north(source,2);
		case SOUTH:
			return Helper::south(source,2);
		case WEST:
			return Helper::west(source,2);
		case EAST:
			return Helper::east(source,2);
		}
	}
}

std::string BoxMovement::toString(){
	std::stringstream ss;

	ss << (bot == RPUSHER? "pusher ": "puller ") << Helper::pair2Str(this->srcBot()) << " " << Helper::pair2Str(this->dest());

	return ss.str();
}

std::string BoxMovement::toConvertedString(int dim){
	std::stringstream ss;

	ss << (bot == RPUSHER? "push ": "pull ") << Helper::pair2Str(std::make_pair<int,int>(this->src().second,dim-this->src().first)) << " " << Helper::pair2Str(std::make_pair<int,int>(this->dest().second,dim-this->dest().first));

	return ss.str();
}
