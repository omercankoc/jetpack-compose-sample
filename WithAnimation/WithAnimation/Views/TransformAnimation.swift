//
//  ContentView.swift
//  WithAnimation
//
//  Created by Ömer Can Koç on 17.07.2022.
//

import SwiftUI

struct TransformAnimation: View {
    
    @State private var transform : Bool = true
    @State private var x : Double = 0.0
    @State private var y : Double = 0.0
    
    var body: some View {
        VStack {
            Image("apple")
                .resizable()
                .aspectRatio(contentMode: .fit)
                .frame(width: 200,
                        height: 200,
                        alignment: .center)
                .transformEffect(CGAffineTransform(translationX: self.x, y: self.y))
                
            Button {
                withAnimation(.easeInOut(duration: 1.0)){
                    if transform {
                        transform = false
                        self.x = -100
                        self.y = -200
                    } else {
                        transform = true
                        self.x = 100
                        self.y = -100
                    }
                }
            } label: {
                Text("START ANIMATE")
                    .padding(.all,10)
                    .foregroundColor(Color.black)
                    .background {
                        RoundedRectangle(cornerRadius: 15)
                            .stroke(Color.black)
                    }
            }
        }.frame(width: UIScreen.main.bounds.width,
                height: UIScreen.main.bounds.height,
                alignment: .center)
        .background(Color.blue)
    }
}

struct TransformAnimation_Previews: PreviewProvider {
    static var previews: some View {
        TransformAnimation()
    }
}
