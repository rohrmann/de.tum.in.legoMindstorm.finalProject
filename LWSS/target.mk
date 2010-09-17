# Makefile include which provides support for different configurations
# 
#Author: Till Rohrmann

.SUFFIXES:

ifeq ($(strip $(TARGET_NAME)),)
OBJDIR:= _$(DEFAULT_TARGET_NAME)
else
OBJDIR:=_$(TARGET_NAME)
endif

MAKETARGET = $(MAKE) --no-print-directory -C $(OBJDIR) \
	-f $(CURDIR)/Makefile SRCDIR=$(CURDIR) CONFIG=$(TCONFIG) $(MAKECMDGOALS)
	
.PHONY : $(OBJDIR)

$(OBJDIR):
	+@[ -d $@ ] || mkdir -p $@
	@$(MAKETARGET)
	
.PHONY:clean

clean:
	-rm -rf $(OBJDIR)
	
.PHONY:distclean

distclean:
	-rm -rf $(wildcard _*)
	-rm -rf $(DEFAULT_DEP_DIR)
	
Makefile : ;
%.mk : ;
%.conf : ;
%.values : ;

% :: $(OBJDIR);
	
	