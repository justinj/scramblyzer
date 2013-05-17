#!/usr/bin/env ruby
require "fileutils"

MAX_LENGTH = 50
COUNT = 10000

SCRAMBLE_KIND = "5gen"

scramble_dir = "scrambles/#{SCRAMBLE_KIND}"

FileUtils.mkdir_p scramble_dir unless Dir.exist? scramble_dir

0.upto(MAX_LENGTH) do |length|
  `ramsdel -c #{COUNT} -e "<R,L,U,F,B> * #{length}" > #{scramble_dir}/len_#{length}`
end
