//
//  OpacityAnimation.swift
//  WithAnimation
//
//  Created by Ömer Can Koç on 17.07.2022.
//

import SwiftUI

struct OpacityAnimation: View {
    
    @State private var opacity : Double = 1.0
    
    var body: some View {
        VStack {
            Image("apple")
                .resizable()
                .aspectRatio(contentMode: .fit)
                .frame(width: 200,
                        height: 200,
                        alignment: .center)
                .opacity(opacity)
                
            Button {
                withAnimation(.easeInOut(duration: 1.0)){
                    if opacity == 0.0 {
                        opacity = 1.0
                    } else if opacity == 1.0 {
                        opacity = 0.0
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
        .background(Color.yellow)
    }
}

struct OpacityAnimation_Previews: PreviewProvider {
    static var previews: some View {
        OpacityAnimation()
    }
}
