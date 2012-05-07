     .  o ..                                    __ _ _             _  _     
     o . o o.o                 _ __  _ __ ___  / _(_) | ___    ___| |(_)___ 
          ...oo               | '_ \| '__/ _ \| |_| | |/ _ \  / __| || / __|
            __[]__            | |_) | | | (_) |  _| | |  __/ | (__| || \__ \
         __|_o_o_o\__         | .__/|_|  \___/|_| |_|_|\___|  \___|_|/ |___/
         \""""""""""/         |_|                                  |__/     
          \. ..  . /          
     ^^^^^^^^^^^^^^^^^^^^ 

Wherein I write a lot of little tests to examine ClojureScript performance, DOM manipulation hacks, &c.

Run
    ./make.sh

to compile CoffeeScript infrastructure, then

    lein cljsbuild once

to compile the ClojureScript and open

    public/run.html

in your favorite browser to run the benchmarks; refresh a few times for "statistical power".
Now open up

    public/index.html

to see the timing results.
You'll need to serve `public` via a webserver or open up Chrome (on OS X) as

    /Applications/Google\ Chrome.app/Contents/MacOS/Google\ Chrome\
      --allow-file-access-from-files \
      --user-data-dir=/tmp


Not afraid of Ruby?

    bundle install
    bundle exec guard

to use the livereload browser plugin.
