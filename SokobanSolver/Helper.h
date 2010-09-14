/*
 * Helper.h
 *
 *  Created on: Sep 14, 2010
 *      Author: rohrmann
 */

#ifndef HELPER_H_
#define HELPER_H_

#include "Field.h"

class Helper {
public:
	Helper();
	virtual ~Helper();

	static Field ch2Field(char ch);
	static char field2Ch(Field field);
};

#endif /* HELPER_H_ */
