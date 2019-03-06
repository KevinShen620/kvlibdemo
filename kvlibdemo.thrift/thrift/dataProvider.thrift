namespace java cpp.yishuihan.provider.thrift

struct Vessel {
	1:i64 mmsi,
	2:string vesselName,
	3:i16 vesselType,
	4:string vendorId,
	5:string callSign,
	6:i32 toBow,
	7:i32 toStern,
	8:i32 toPort,
	9:i32 toStarBoard,
	10:i64 motherShipMmsi,
	11:i64 timestamp
}

struct VesselList {
	1:i64 totalNumber,
	2:i64 elapsedTime,
	3:i64 qtime,
	4:list<Vessel> vessels
}

struct Voyage {
	1:i64 mmsi,
	2:i64 timestamp,
	3:i64 imo,
	4:string callSign,
	5:string shipName,
	6:i16 shipType,
	7:i32 toBow,
	8:i32 toStern,
	9:i32 toPort,
	10:i32 toStarBoard,
	11:i16 epfd,
	12:string eta,
	13:i32 draught,
	14:string destination
}

struct VoyageList {
	1:i64 totalNumber,
	2:i64 elapsedTime,
	3:i64 qtime,
	4:list<Voyage> voyages
}

struct Point {
	1:double longitude, //in degrees
	2:double latitude //in degrees
}

struct Track {
	1:i64 mmsi,
	2:i64 timestamp, //in seconds
	3:Point location,
	4:i32 direction, //in 1/10 degree
	5:i32 speed //in 1/10 nmi
}

struct Circle {
	1:Point center,
	2:double radius
}

struct Rectangle {
	1:Point leftBottom,
	2:Point rightTop
}

struct Polygon {
	1:list<Point> points
}

struct TrackList {
	1:i64 totalNumber,
	2:i64 start,
	3:i64 rows,
	4:i64 elapsedTime,
	5:i64 qtime,
	6:i64 composeTime,
	7:list<Track> tracks
}

service TDataProvider {
	//get vessel info
	VesselList getVessel(1:i64 mmsi),
	VesselList getVesselByName(1:string vesselName),
	
	//get voyage info
	VoyageList getVoyage(1:i64 mmsi, 2:i64 startTime, 3:i64 endTime),
	
	//get track
	//single vessel
	TrackList getVesselTrack(1:i64 mmsi, 2:i64 startTime, 3:i64 endTime, 4:i32 start, 5:i32 rows),
	TrackList getVesselTrackLastDay(1:i64 mmsi, 2:i32 start, 3:i32 rows),
	TrackList getVesselTrackLast2Day(1:i64 mmsi, 2:i32 start, 3:i32 rows),
	TrackList getVesselTrackLastWeek(1:i64 mmsi, 2:i32 start, 3:i32 rows),
	
	//multi-vessels
	TrackList getVesselsTrack(1:list<i64> mmsis, 2:i64 startTime, 3:i64 endTime, 4:i32 start, 5:i32 rows),
	TrackList getVesselsTrackLastDay(1:list<i64> mmsis, 2:i32 start, 3:i32 rows),
	TrackList getVesselsTrackLast2Day(1:list<i64> mmsis, 2:i32 start, 3:i32 rows),
	TrackList getVesselsTrackLastWeek(1:list<i64> mmsis, 2:i32 start, 3:i32 rows),
	
	//geo circle
	TrackList getTrackInCircle(1:Circle circle, 2:i64 startTime, 3:i64 endTime, 4:i32 start, 5:i32 rows),
	TrackList getTrackInCircleLastDay(1:Circle circle, 2:i32 start, 3:i32 rows),
	TrackList getTrackInCircleLast2Day(1:Circle circle, 2:i32 start, 3:i32 rows),
	TrackList getTrackInCircleLastWeek(1:Circle circle, 2:i32 start, 3:i32 rows),
	
	//geo rectangle
	TrackList getTrackInRectangle(1:Rectangle rectangle, 2:i64 startTime, 3:i64 endTime, 4:i32 start, 5:i32 rows),
	TrackList getTrackInRectangleLastDay(1:Rectangle rectangle, 2:i32 start, 3:i32 rows),
	TrackList getTrackInRectangleLast2Day(1:Rectangle rectangle, 2:i32 start, 3:i32 rows),
	TrackList getTrackInRectangleLastWeek(1:Rectangle rectangle, 2:i32 start, 3:i32 rows),
	
	//geo polygon
	TrackList getTrackInPolygon(1:Polygon polygon, 2:i64 startTime, 3:i64 endTime, 4:i32 start, 5:i32 rows),
	TrackList getTrackInPolygonLastDay(1:Polygon polygon, 2:i32 start, 3:i32 rows),
	TrackList getTrackInPolygonLast2Day(1:Polygon polygon, 2:i32 start, 3:i32 rows),
	TrackList getTrackInPolygonLastWeek(1:Polygon polygon, 2:i32 start, 3:i32 rows),
	
	//realtime track
	TrackList getRealTrackInRectangle(1:Rectangle rectangle, 2:i32 start, 3:i32 rows),
	TrackList getRealTrackInPolygon(1:Polygon polygon, 2:i32 start, 3:i32 rows),
	
	//test map/reduce
	TrackList getVesselTrackByNameAndMapReduce(1:string vesselName, 2:i64 startTime, 3:i64 endTime, 4:i32 start, 5:i32 rows)
}
