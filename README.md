# OpenGaia

OpenGaia is a free and open source GIS based on [openstreetmap](https://www.openstreetmap.org) databases.

## Installation

Download from source, build with Gradle and run the main.
You'll find all the dependencies inside build.gradle.

## Usage

Outside src there should be some files I haven't uploaded yet due to data protection reasons.
There should be two folders named respectively "landuse_textures" containing all the textures
for the landuse data, and the same thing goes for the "leisure_textures" folder. Moreover,
for now, you will have to go to [openstreetmap](https://www.openstreetmap.org) and manually
download a small map tile of the region you want to render.

## Contributing

Right now it's still a prototype, any contribution is appreciated.
Pull requests are welcome. For major changes, please open an issue first to discuss what you would like to change.

Check the to-do list down here if you want to contribute!

## TODO

- Create textures
- Fix graphical inaccuracies while rendering
- Retrieve all the entities (some are missing like areas or bus stops)
- Set the openstreetmap logo as they say on their licence
- Create a more efficient map parser so that huge files can be rendered
- Altitude map using geodesy
- Make a better UI
