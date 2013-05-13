#!/usr/bin/env ruby

MAX_LENGTH = 50
COUNT = 10000

0.upto(MAX_LENGTH) do |length|
  `ramsdel -c #{COUNT} -e "<R,L,U,D,F,B> * #{length}" > data/scrambles_len_#{length}`
end
