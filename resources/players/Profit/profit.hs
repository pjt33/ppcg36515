-- Copyright (c) 2014 Threefx http://codegolf.stackexchange.com/users/21038/threefx
import System.Environment (getArgs)

main = putStrLn . trade . map read =<< getArgs

trade :: [Integer] -> String
trade [p,m,_] -- guess the letter ;)
  | p == 1 = "B" ++ (show m)
  | otherwise = "W"
