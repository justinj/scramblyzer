#!/usr/bin/env ruby

0.upto(50) do |length|
  `ramsdel -c 1000 -e "<R,L,U,D,F,B> * #{length}" > data/scrambles_len_#{length}`
end
