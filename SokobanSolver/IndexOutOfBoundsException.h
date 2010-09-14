/*
 * IndexOutOfBoundsException.h
 *
 *  Created on: Sep 14, 2010
 *      Author: rohrmann
 */

#ifndef INDEXOUTOFBOUNDSEXCEPTION_H_
#define INDEXOUTOFBOUNDSEXCEPTION_H_

#include <exception>
#include <string>

class IndexOutOfBoundsException: public std::exception {
private:
	std::string message;
public:
	IndexOutOfBoundsException(const std::string& message = "") throw() :message(message) {};
	virtual ~IndexOutOfBoundsException() throw();

	const char* what(){
		return message.c_str();
	}

};

#endif /* INDEXOUTOFBOUNDSEXCEPTION_H_ */
