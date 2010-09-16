/*
 * InvalidArgumentException.h
 *
 *  Created on: Sep 14, 2010
 *      Author: rohrmann
 */

#ifndef INVALIDARGUMENTEXCEPTION_H_
#define INVALIDARGUMENTEXCEPTION_H_

#include <exception>
#include <string>

class InvalidArgumentException : public std::exception {
private:
	std::string message;
public:
	InvalidArgumentException(const std::string& message="") throw();
	virtual ~InvalidArgumentException() throw();

	const char* what() const throw(){
		return message.c_str();
	}
};

#endif /* INVALIDARGUMENTEXCEPTION_H_ */
