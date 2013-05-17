#!/usr/bin/env ruby

MAX_LENGTH = 50
COUNT = 10000

SCRAMBLE_KIND = "6gen"

0.upto(MAX_LENGTH) do |length|
  `ramsdel -c #{COUNT} -e "<R,L,U,D,F,B> * #{length}" > scrambles/#{SCRAMBLE_KIND}/len_#{length}`

end
