/*
 * InvalidArgumentException.cpp
 *
 *  Created on: Sep 14, 2010
 *      Author: rohrmann
 */

#include "InvalidArgumentException.h"

InvalidArgumentException::InvalidArgumentException(const std::string& message) throw() :message(message) {

}

InvalidArgumentException::~InvalidArgumentException() throw() {
}
