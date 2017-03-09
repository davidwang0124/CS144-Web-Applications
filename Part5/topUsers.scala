val text = sc.textFile("twitter.edges")
val lines = text.map( line => line.split(": ") )
val parsedLines = lines.flatMap( line => {
    val followings = line(1)
    val followingIDs = followings.split(",")
    followingIDs.map( following => (following, 1) )
} )
val followerCounts = parsedLines.reduceByKey( (a,b) => a+b )
val finalResults = followerCounts.filter ( x => {
	val count = x._2
	(count > 1000)
} )
finalResults.saveAsTextFile("output")
