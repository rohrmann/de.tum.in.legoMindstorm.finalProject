/*
 * FlowTester.h
 *
 *  Created on: Sep 19, 2010
 *      Author: rohrmann
 */

#ifndef FLOWTESTER_H_
#define FLOWTESTER_H_

class FlowTester{
private:
	bool* accessible;
	point dims;
public:
	FlowTester(bool* accessible, point dims){
		this->accessible = accessible;
		this->dims = dims;
	}

	unsigned int addr(point coords){
		return coords.first*dims.second + coords.second;
	}

	bool operator()(point coords){
		if(coords.first >= 0 && coords.second >= 0 && coords.first < dims.first && coords.second < dims.second && accessible[addr(coords)])
			return true;

		return false;
	}
};

#endif /* FLOWTESTER_H_ */
