<?php
// Copyright (c) 2014 Sammitch http://codegolf.stackexchange.com/users/13257/sammitch
$cur = array('price' => $argv[1], 'funds' => $argv[2], 'shares' => $argv[3]);

$cachefile = 'cache.json';
if( ! file_exists($cachefile) ) { $cache = array(); }
else { $cache = json_decode(file_get_contents($cachefile), true); }

// determine action
if( empty($cache) ) {
    $action = 'buy'; // always buy on first turn
} else if( $cur['price'] > $cache[count($cache)-1]['price'] ) {
    $action = 'buy';
} else if( $cur['price'] < $cache[count($cache)-1]['price'] ) {
    $action = 'sell';
} else {
    $action = 'hold';
}

// determine volume
if( $action == 'hold' ) {
    $volume = 0;
} else if( $action == 'buy' ) {
    // spend half my money on shares!
    $volume = floor(($cur['funds']/2)/$cur['price']);
} else if( $action == 'sell' ) {
    // sell half my shares!
    $volume = floor($cur['shares']/2);
}

// do a thing!
if( $action == 'hold' ) { echo 'W'; }
else if( $action == 'buy' ) { echo "B $volume"; }
else { echo "S $volume"; }
echo "\n";

$cache[] = $cur;
if( count($cache) == 50 ) { unlink($cachefile); } // wipe cache on last turn
else { file_put_contents($cachefile,json_encode($cache)); } // write cache
