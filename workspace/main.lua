function main(context, data)

print("lua main func ran")
-- set watermark
context:addWatermark("/home/slayer/Desktop/workspace/watermark.png", 100, 100, 0, 0, 0.5, 0.5)

-- add audio
--local audio1 = AudioManager.addTrack(context, "/home/slayer/Desktop/workspace/music.mp3")
local audio2 = AudioManager.addTrack(context, "/home/slayer/Desktop/workspace/china_style.mp3")
--AudioManager.setOutPoint(context, audio2, 50)


print("audio tracks size :", AudioManager.numTracks(context))

end