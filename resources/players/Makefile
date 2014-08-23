all:
	for dir in `find -mindepth 1 -maxdepth 1 -type d`; do \
		$(MAKE) -C $$dir || exit ; \
	done

clean:
	rm -f *.txt *.log *.json
	for dir in `find -mindepth 1 -maxdepth 1 -type d`; do \
		$(MAKE) -C $$dir clean || exit ; \
	done
