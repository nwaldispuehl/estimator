# Estimator

Estimates how many times some event occurs at the end of a time frame based on how many times it occurred so far.

## Introduction

In a give time frame (`[t_start, t_end]`) we have a number of events which we assume happen based on a given distribution, for example a [normal distribution](https://en.wikipedia.org/wiki/Normal_distribution). Their occurrence at some time (`t_i`) is reflected in a [probability density function (PDF)](https://en.wikipedia.org/wiki/Probability_density_function) as shown here:

<img src="https://github.com/nwaldispuehl/estimator/raw/master/footage/pdf.png" alt="probability density function" width="500" />

The total number of events who have occurred so far at a given time (`t_i`) is reflected in the [cumulative distribution function (CDF)](https://en.wikipedia.org/wiki/Cumulative_distribution_function) - which is just an integration over the PDF - as depicted here:

<img src="https://github.com/nwaldispuehl/estimator/raw/master/footage/cdf.png" alt="cumulative density function" width="500" />

We now have a known time frame (`[t_start, t_end]`) and we also know how many events have occurred so far at some point in time (`t_i`) and we assume some distribution. We now want to estimate how many events there will be at the end of the time frame:
 
<img src="https://github.com/nwaldispuehl/estimator/raw/master/footage/cdf_estimation.png" alt="estimation" width="500" />

This is what the Estimator does.

## Screenshot

![Estimator screenshot](https://github.com/nwaldispuehl/estimator/raw/master/footage/estimator_screenshot.png)

## Supported distributions

The software currently supports these distributions:

### Uniform

The uniform distribution assumes linear growth:

<img src="https://github.com/nwaldispuehl/estimator/raw/master/footage/plot_uniform_cdf_x.png" alt="uniform cdf" width="500" />

### Normal

Two 'flavours' of normal distributions are provided.

#### SD 2

The normal distribution with standard deviation 2 only covers around 95% of all events (thus cutting the earliest and last 2.5%). It models real world events better than the more accurate variant below.

<img src="https://github.com/nwaldispuehl/estimator/raw/master/footage/plot_normal_cdf_0.25.png" alt="normal sd 2 cdf" width="500" />

Note that we only consider the range in `[0, 1]`.

#### SD 3

Normal distribution with a standard deviation of 3.

<img src="https://github.com/nwaldispuehl/estimator/raw/master/footage/plot_normal_cdf_0.17.png" alt="normal sd 3 cdf" width="500" />

## How to use?

### Download 

There is a pre-built distribution on the github releases page: [Estimator releases](https://github.com/nwaldispuehl/estimator/releases). 
Download the latest archive (e.g. `estimator.app-0.0.1.zip`) and unzip it somewhere on your hard drive.

### Run

Run the start script from the command line:

    $ cd estimator.app-0.0.1/bin
    $ ./estimator.app


### Preferences file

The software creates and uses a single preferences file where it keeps the currently entered values. It has the name `currentState` and is located in the users home directory in a folder `.estimator`, thus:

    ~/.estimator/currentState
    
It is updated on every user entry.

## How to build?

You need a Java 8 (version > 1.8.0_60) development kit installed on your system. Then, clone the repository: 

    $ git clone https://github.com/nwaldispuehl/estimator.git
    $ cd estimator/

and use the packaged gradle binary to either run the program directly,

    $ ./gradlew run
  
execute its tests, 

    $ ./gradlew check
  
or create the distribution as zip file:

    $ ./gradlew distZip
  
The distribution archive is then to be found in the `estimator.app/build/distributions/` folder.


## Current build status

[![Build Status](https://travis-ci.org/nwaldispuehl/estimator.svg?branch=master)](https://travis-ci.org/nwaldispuehl/estimator)
