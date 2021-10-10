Improvements needed.

1. During cold start(first launch), i show error scrren until the data is fetched instead of progress dialog/shimmer ui.
2. Moshi or gson or other libs are not used due to shortage of time. Only 2 models files for POJOS are present - for db and network.Mapping are very basic.
3. UI is very basic and hard codings are present. Colors, dimens, styles are hardcoded in layout xmls.
4. Error codes, network and other constants are added in different places. A small refctoring for these is required. 
5. Dependency injection and testing code is not added.
