//
//  ScaleAnimation.swift
//  WithAnimation
//
//  Created by Ömer Can Koç on 17.07.2022.
//

import SwiftUI

struct ScaleAnimation: View {
    
    @State private var scale : Bool = true
    
    var body: some View {
        VStack {
            Image("apple")
                .resizable()
                .aspectRatio(contentMode: .fit)
                .frame(width: 200,
                        height: 200,
                        alignment: .center)
                .scaleEffect(scale ? 1.0 : 0.0)
                
            Button {
                withAnimation(.easeInOut(duration: 1.0)){
                    if scale {
                        scale = false
                    } else {
                        scale = true
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
        .background(Color.orange)
    }
}

struct ScaleAnimation_Previews: PreviewProvider {
    static var previews: some View {
        ScaleAnimation()
    }
}
