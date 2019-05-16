function main(context, data)
    print("lua main ran")

    context:addWatermark("/home/slayer/Desktop/workspace/watermark.png", 100, 100, 0, 0, 0.5, 0.5)
    local audio1 = AudioManager.addTrack(context, "/home/slayer/Desktop/workspace/music.mp3")
    local audio2 = AudioManager.addTrack(context, "/home/slayer/Desktop/workspace/result.aac")


    print("duration 1 :", AudioManager.duration(context, audio1))

    if (audio2) then
        print("duration 2 :", AudioManager.duration(context, audio2))
    end

end