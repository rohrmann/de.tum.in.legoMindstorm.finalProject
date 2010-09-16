/*
 * Solver.h
 *
 *  Created on: Sep 15, 2010
 *      Author: rohrmann
 */

#ifndef SOLVER_H_
#define SOLVER_H_

class AbstractGameMap;
class Move;

class Solver {
public:
	Solver();
	virtual ~Solver();

	Move* solve(AbstractGameMap& map);
};

#endif /* SOLVER_H_ */
