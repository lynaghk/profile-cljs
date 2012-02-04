     .  o ..                                    __ _ _             _  _     
     o . o o.o                 _ __  _ __ ___  / _(_) | ___    ___| |(_)___ 
          ...oo               | '_ \| '__/ _ \| |_| | |/ _ \  / __| || / __|
            __[]__            | |_) | | | (_) |  _| | |  __/ | (__| || \__ \
         __|_o_o_o\__         | .__/|_|  \___/|_| |_|_|\___|  \___|_|/ |___/
         \""""""""""/         |_|                                  |__/     
          \. ..  . /          
     ^^^^^^^^^^^^^^^^^^^^ 

Wherein I write a lot of little tests to examine ClojureScript performance, DOM manipulation hacks, &c.

To play along at home, you'll need to install PhantomJS.
OS X users:

    brew update && brew install phantomjs

Everyone else, you're on your own; see http://www.phantomjs.org/

Then the usual:

    git submodule update
    cd vendor/clojurescript && script/bootstrap
    lein deps

Finally, run 

    ./make.sh

to compile the ClojureScript and execute with PhantomJS.
