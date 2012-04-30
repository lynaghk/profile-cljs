     .  o ..                                    __ _ _             _  _     
     o . o o.o                 _ __  _ __ ___  / _(_) | ___    ___| |(_)___ 
          ...oo               | '_ \| '__/ _ \| |_| | |/ _ \  / __| || / __|
            __[]__            | |_) | | | (_) |  _| | |  __/ | (__| || \__ \
         __|_o_o_o\__         | .__/|_|  \___/|_| |_|_|\___|  \___|_|/ |___/
         \""""""""""/         |_|                                  |__/     
          \. ..  . /          
     ^^^^^^^^^^^^^^^^^^^^ 

Wherein I write a lot of little tests to examine ClojureScript performance, DOM manipulation hacks, &c.

Change the cljsbuild configuration in `project.clj` to build the test that you're interested in, then run

    lein cljsbuild once

to compile the ClojureScript and open

    public/run.html

in your favorite browser.


Not afraid of Ruby?

    bundle install
    bundle exec guard

to use the livereload browser plugin.
