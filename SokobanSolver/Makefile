ifeq (,$(filter _%,$(notdir $(CURDIR))))

DEFAULT_VALUES=default.values
include $(DEFAULT_VALUES)

#if no configuration specified, then take the default one
ifeq ($(strip $(CONFIG)),$(DEFAULT_VALUE))
CONFIG:=$(DEFAULT_CONFIG)
else
	ifeq ($(strip $(CONFIG)),)
		CONFIG:=$(DEFAULT_CONFIG)
	endif
endif

ifeq ($(CONFIG),$(notdir $(CONFIG)))
	TCONFIG=$(CURDIR)/$(CONFIG)
else
	TCONFIG=$(CONFIG)
endif


#include the specific configuration
include $(TCONFIG)

include target.mk

else

VPATH=$(SRCDIR)
DEFAULT_VALUES=$(SRCDIR)/default.values

include $(DEFAULT_VALUES)

DEPEND:=$(SRCDIR)/$(DEFAULT_DEPEND)
DEP:=$(SRCDIR)/$(DEFAULT_DEP_DIR)
MAKEFILE_RULES=$(SRCDIR)/$(DEFAULT_MAKEFILE_RULES)

include $(CONFIG)

SRC:= $(notdir $(wildcard $(SRCDIR)/*.cpp))
OBJECTS:=$(patsubst %.cpp,%.o,$(filter-out main% test%,$(SRC)))
MAIN:=$(patsubst %.cpp,%.o,$(filter main%,$(SRC)))
TEST:=$(patsubst %.cpp,%.o,$(filter test%,$(SRC)))

HEADER:=
CXXFLAGS+= $(HEADER)
CFLAGS+= $(HEADER)
LDFLAGS+=
LIBS+=

SokobanSolver: $(MAIN) $(OBJECTS)
	$(CXX) $(LDFLAGS) -o $@ $(MAIN) $(OBJECTS) $(LIBS)

include $(MAKEFILE_RULES)
	

endif
