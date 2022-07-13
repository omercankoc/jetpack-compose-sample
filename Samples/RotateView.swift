import SwiftUI

struct RotateView: View {
    
    @State private var rotate : Bool = true
    
    var body: some View {
        VStack {
            Image("image")
                .frame(width: 200,
                        height: 200,
                        alignment: .center)
                .rotationEffect(Angle(degrees: rotate ? 0 : 360.0))
                
            Button {
                withAnimation(.easeInOut(duration: 1.0)){
                    if rotate {
                        rotate = false
                    } else {
                        rotate = true
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
        .background(Color.green)
    }
}
